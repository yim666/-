<template>
  <div>
    <div>
      订单日期: <a-range-picker ref="adminRange"  allow-clear @change="onChange" />

      <span>车牌号: </span>
      <a-select  style="width: 120px" @change="carIdChange"  allowClear show-search>
        <a-select-option v-for="item in userList" :key="item.carId" >
          {{ item.carId }}
        </a-select-option>
      </a-select>

      <a-button @click="Reset" type="primary">
        <template #icon><SearchOutlined /></template>
        Search All
      </a-button>
    </div>

    <div>
      <br/>
      <a-input prefix="￥" suffix="" v-model="sumMoney" autosize disabled
      style="width: 6%;margin-left:50%"/>
    </div>
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
        orderList:[],
        pagination:{
          pageSize:7,
          hideOnSinglePage:true
        },
        SelectOrder:{
          userId: '0',
          dateRange:[]
        },
        userList:[],
        carList:[],
        sumMoney:0
      }
    },
    methods:{
      Reset(){
        this.selectOrderList()
        // this.selcetUserList()
      },
      selcetUserList(){
        this.$axios.get('/api/admin/selectUserList').then(res=>{
          this.userList=res.data.data
          var n = [];
          for(var i=0;i<this.userList.length-1;i++){
            if(this.userList.indexOf(this.userList[i]) == i){
              n.push(this.userList[i]);
            }
          }
          this.userList=n
        })
      },
      carIdChange(carId){
         this.$axios.get('/api/admin/selectOrderListByCarId',{params:{carId:carId}}).then(res=>{
          this.orderList=res.data.data
           this.sumMoney=0
          for(var i=0;i<this.orderList.length;i++){
            if(this.orderList[i].status==0){
              this.orderList[i].status='订单已完成'
            }
            if(this.orderList[i].status==1 || this.orderList[i].status==2){
              this.orderList[i].status='订单进行中'
            }
            this.sumMoney+=this.orderList[i].fee
          }
        })
      },
      onChange(date, dateString){
        console.log(date, dateString);
        if(date !=''){
          this.SelectOrder.dateRange=dateString
          this.$axios({
            url:'/api/user/selectMyorderListByDate',
            method:"post",
            data:this.SelectOrder
          }).then(res=>{
            this.orderList=res.data.data
            this.sumMoney=0
            for(var i=0;i<this.orderList.length;i++){
              if(this.orderList[i].status==0){
                this.orderList[i].status='订单已完成'
              }
              if(this.orderList[i].status==1 || this.orderList[i].status==2){
                this.orderList[i].status='订单进行中'
              }
              this.sumMoney+=this.orderList[i].fee
            }
          })
        }
        else{
          this.selectOrderList()
        }

      },

      async selectOrderList(){
        await this.$axios.get('/api/admin/selectOrderList').then(res=>{
          this.orderList=res.data.data
          this.sumMoney=0
          for(var i=0;i<this.orderList.length;i++){
            if(this.orderList[i].status==0){
              this.orderList[i].status='订单已完成'
            }
            if(this.orderList[i].status==1 || this.orderList[i].status==2){
              this.orderList[i].status='订单进行中'
            }
            this.sumMoney+=this.orderList[i].fee
          }
        })
      },
      // thisTime(){
      //   // 实现局部刷新
      //   setInterval(()=>{
      //     this.selectOrderList()
      //   },5000)
      // }
    },
    created() {
      this.selectOrderList()
      this.selcetUserList()
      // this.thisTime()
    }
  }
</script>

<style scoped>

</style>
