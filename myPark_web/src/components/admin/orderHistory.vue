<template>
  <div>
  <a-table  :data-source="this.orderList" :columns="columns" bordered
            style="margin-right:15%;margin-left:15% "
            :pagination="pagination" hideOnSinglePage>
  </a-table>
  </div>
</template>

<script>
  const columns = [
    {
      title: '用户名',
      dataIndex: 'userId',
      align:'center'
    },
    {
      title: '停车位',
      dataIndex: 'parkingSpaceId',
      align:'center'
    },
    {
      title: '车牌号',
      dataIndex: 'carId',
      align:'center'
    },{
      title: '订单创建时间',
      dataIndex: 'createTime',
      width:200,
      align:'center'
    },{
      title: '订单结束时间',
      dataIndex: 'endTime',
      width:200,
      align:'center'
    },{
      title: '停车费',
      dataIndex: 'fee',
      align:'center'
    },{
      title: '订单状态',
      dataIndex: 'status',
      align:'center'
    },
  ];
  export default {
    name: "orderHistory",
    data(){
      return{
        columns,
        orderList:[],
        pagination:{
          pageSize:5,
          hideOnSinglePage:true
        }
      }
    },
    methods:{
      async selectOrderList(){
        await this.$axios.get('/api/admin/selectOrderList').then(res=>{
          this.orderList=res.data.data
          for(var i=0;i<this.orderList.length;i++){
            if(this.orderList[i].status==0){
              this.orderList[i].status='订单进行中'
            }
            if(this.orderList[i].status==1){
              this.orderList[i].status='订单已完成'
            }
           }
        })
      }
    },
    created() {
      this.selectOrderList()
    }
  }
</script>

<style scoped>

</style>
