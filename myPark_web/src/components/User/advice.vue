<template>
<div>
  <a-button type="primary" @click="()=> visible =true">投诉建议</a-button>

  <!--  公告内容弹窗-->
  <a-modal title="投诉建议" :visible="visible" @cancel="()=>(visible=false,this.content='')">
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

  <a-list
    :header="`我一共投诉了${adviceList.length} 次`"
    item-layout="horizontal"
    :data-source="adviceList"
    style="margin-right:20%;margin-left:20% "
    :pagination="pagination"
  >
    <a-list-item slot="renderItem" slot-scope="item, index">
      <a-comment :author="item.userId" :avatar="avatar">
        <p slot="content">
          {{ item.content }}
        </p>
        <a-tooltip slot="datetime" :title="item.time">
          <span>{{item.time}}</span>
        </a-tooltip>
      </a-comment>
    </a-list-item>
  </a-list>


</div>
</template>

<script>
  export default {
    name: "upAdvice",
    data(){
      return{
        visible:false,
        content:'',
        writeAdvice:{
          id: null,
          content: '',
          userId: this.$cookies.get("cid")
        },
        adviceList:[],
        avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
        pagination:{
          pageSize:2
        }
      }
    },
    methods:{
      selectMyAdvice(){
        this.$axios.get('/api/user/selectMyAdvice',{params:{id:this.writeAdvice.userId}}).then(res=>{
          this.adviceList=res.data.data
        })
      },
      onSubmit(){
        this.writeAdvice.content=this.content
        this.$axios.post('/api/user/createAdvice',this.writeAdvice).then(res=>{
          if(res.data.data === null || res.data.data === ''){
            this.$message.warn("投诉失败！！！")
            return
          }
          this.$message.success("投诉成功！！！")
          this.visible=false
          this.content=''
          this.selectMyAdvice()
        })
      },
      resetForm(){
        this.content=''
      }
    },
    created() {
      this.selectMyAdvice()
    }
  }
</script>

<style scoped>

</style>
