<template>
  <div>
  <a-button type="primary" @click="()=> visible =true">发布公告</a-button>

<!--  公告内容弹窗-->
    <a-modal title="发布公告" :visible="visible" @cancel="()=>(visible=false)">
  <a-form-model ref="writeForm"  >
    <a-form-model-item  >
      <a-textarea v-model="content" :auto-size="{ minRows: 5, maxRows: 10 }" />
    </a-form-model-item>
  </a-form-model>
      <template slot="footer">
        <a-button type="primary" @click="onSubmit">
          Create
        </a-button>
        <a-button style="margin-left: 10px;" @click="resetForm">
          Reset
        </a-button>
      </template>
    </a-modal>

<!--    :header="`共有${noticeList.length} 条公告`"-->
    <a-list
      item-layout="horizontal"
      :data-source="noticeList"
      style="margin-right:20%;margin-left:20%;"
    >
      <a-list-item slot="renderItem" slot-scope="item, index">
        <a-comment :author="item.adminId" :avatar="avatar">
          <p slot="content">
            {{ item.content }}
          </p>
          <a-tooltip slot="datetime" :title="item.time">
            <span>{{item.time}}</span>
          </a-tooltip>
        </a-comment>
      </a-list-item>
      <a-pagination  style="margin-bottom: 10%" v-model="pageInfo.current" :page-size="pageInfo.pageSize"
                     :total="pageInfo.total" @change="handleCurrentChange"/>
    </a-list>

  </div>
</template>

<script>
  import moment from 'moment';
  export default {
    name:'adminNotice',
    data() {
      return {
        avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
        moment,
        visible: false,
        content: '',
        noticeList:[],
        writeNotice:{
          id: null,
          content: '',
          adminId: this.$cookies.get("cid")
        },
        pageInfo:{
          pageSize:3,
          total: 10,
          current: 1,
        }

      };
    },
    methods:{
      handleCurrentChange(current,pageSize){
        this.current =current
        this.selectNotice()
      },
      selectNotice(){
        //后端通过sql limit实现分页
        this.$axios.get('/api/admin/selectNoticeList',{params:this.pageInfo}).then(res=>{
          this.noticeList=res.data.data.data
          this.pageInfo.total=res.data.data.total
        })
      },
      onSubmit(){
        this.writeNotice.content=this.content
        this.$axios.post('/api/admin/createNotice',this.writeNotice).then(res=>{
          if(res.data.data === null || res.data.data === ''){
            this.$message.warn("发布公告失败！！！")
            return
          }
          this.selectNotice()
          this.$message.success("发布公告成功！！！")
          this.visible=false
        })
      },
      resetForm(){
        this.content=''
      }
    },
    created() {
      this.selectNotice()
    }
  }
</script>


<style  scoped>

</style>

