<template>
  <div>
    <div>
      订单日期: <a-range-picker @change="onChange"  allowClear/>
      <a-button @click="selectMyorderList" type="primary">sellectAll</a-button>
    </div>
  <a-table  :data-source="this.myOrderList" :columns="columns" bordered
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
      title: '停车场',
      dataIndex: 'parkingLotName',
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
        myOrderList:[],
        userId: this.$cookies.get("uid"),
        pagination:{
          pageSize:8,
          hideOnSinglePage:true
        },
        SelectOrder:{
          userId:this.$cookies.get("uid"),
          dateRange:[]
        }
      }
    },
    methods:{
      onChange(date, dateString){
        console.log(date, dateString);
        this.SelectOrder.dateRange=dateString
        if(date.length==0){
          this.selectMyorderList()
          return
        }
        this.$axios({
          url:'/api/user/selectMyorderListByDate',
          method:"post",
          data:this.SelectOrder
        }).then(res=>{
          this.myOrderList=res.data.data
          for(var i=0;i<this.myOrderList.length;i++){
            if(this.myOrderList[i].status==0){
              this.myOrderList[i].status='订单已完成'
            }
            if(this.myOrderList[i].status==1 || this.myOrderList[i].status==2){
              this.myOrderList[i].status='订单进行中'
            }
          }
        })
      },
      async selectMyorderList(){
        await this.$axios.get('/api/user/selectMyorderList',{params:{userId:this.userId}}).then(res=>{
          this.myOrderList=res.data.data
          for(var i=0;i<this.myOrderList.length;i++){
            if(this.myOrderList[i].status==0){
              this.myOrderList[i].status='订单已完成'
            }
            if(this.myOrderList[i].status==1 || this.myOrderList[i].status==2){
              this.myOrderList[i].status='订单进行中'
            }
           }
        })
      },
      // thisTime(){
      //   // 实现局部刷新
      //   setInterval(()=>{
      //     this.selectMyorderList()
      //   },5000)
      // }
    },
    mounted() {
      this.selectMyorderList()
      // this.thisTime()
    }
  }
</script>

<style scoped>

</style>
