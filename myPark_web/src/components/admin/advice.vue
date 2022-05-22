<template>
  <a-list
    :header="`${adviceList.length} 条投诉`"
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

</template>

<script>
  import moment from 'moment';
  export default {
    name:'advice',
    data() {
      return {
        avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
        moment,
        adviceList:[],
        pagination:{
          pageSize:3
        }
      };
    },
    methods:{
        selectAdvice(){
          //前端框架（ant-design）实现分页
          this.$axios.get('/api/admin/selectAdviceList').then(res=>{
            this.adviceList=res.data.data
          })
        }
    },
    created() {
      this.selectAdvice()
    }
  }
</script>


<style  scoped>

</style>

