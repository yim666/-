<template>
  <a-list
    :header="`${noticeList.length} 条公告`"
    item-layout="horizontal"
    :data-source="noticeList"
    style="margin-right:20%;margin-left:20% "
    :pagination="pagination"
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
  </a-list>

</template>

<script>
  import moment from 'moment';
  export default {
    name:'notice',
    data() {
      return {
        avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
        moment,
        noticeList:[],
        pagination:{
          pageSize:3
        }
      };
    },
    methods:{
        selectNotice(){
          //前端框架（ant-design）实现分页
          this.$axios.get('/api/user/selectNoticeList').then(res=>{
            this.noticeList=res.data.data
          })
        }
    },
    created() {
      this.selectNotice()
    }
  }
</script>


<style  scoped>

</style>

