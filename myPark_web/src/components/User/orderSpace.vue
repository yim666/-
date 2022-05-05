<template>
  <div >
<!--    查看当前车位情况-->
      <a-button type="primary" @click="showDrawer" icon="search" style="position: relative;align:center;z-index: 5">
        查看车位
      </a-button>
      <a-button type="primary" @click="showDrawer2" style="position: relative;align:center;margin-left:20px;z-index: 5">
        <a-icon type="home" />
        当前订单
      </a-button>

<!--      车位抽屉-->
      <a-drawer
        title="车位情况"
        placement="right"
        :closable="false"
        :visible="visible"
        :width="500"
        :after-visible-change="afterVisibleChange"
        @close="onClose"
      >

        <h2 style="color: tan;margin: 50px"><a-icon type="clock-circle" />  停车费￥10/h ,不满1h按1h计算</h2>
        <a-card :title="title" hoverable >
          <a-popover v-for="item in parkingLot" :key="item.parkingLotId">
            <template slot="content">
              <p>{{item.parkingLotName}}</p>
            </template>
          <a-card-grid style="width:33.33%;text-align:center;color: blue" @click="order(item)">
            {{item.parkingLotName}}
          </a-card-grid>
          </a-popover>
        </a-card>

        <a-card style="margin-top: 10%">
          <p>{{nowParkingLot.parkingLotName}}还剩{{lotSpaceLength}}个停车位</p>
          <span>请选择您想要预定的停车位: </span>
          <a-select  style="width: 120px" @change="selectChange"  >
            <a-select-option v-for="item in lotSpace" :key="item.parkingSpaceId" >
              {{ item.parkingSpaceId }}
            </a-select-option>
          </a-select>
          <a-button type="primary" :disabled="flag" @click="()=>(visible2=true)">预定车位</a-button>
        </a-card>

        <a-modal title="温馨提示" :visible="visible2" @cancel="()=>(visible2=false)" @ok="orderCreate"
        okText="确认预定" cancelText="我再想想">
          <p style="color: blue">预定成功后，请在十五分钟内变更订单状态为停车中，否则将自动取消订单并释放车位</p>
          <h3 style="color: red ">是否继续预定？</h3>
        </a-modal>
      </a-drawer>

    <a-drawer
      title="当前订单"
      placement="bottom"
      :closable="false"
      :visible="visible3"
      @close="onClose"
    >
      <a-table
        :columns="innerColumns"
        :data-source="this.innerData"
        :pagination="false"
      >
      <span slot="operation" slot-scope="text, record, index" @click="()=>rowSpace=record">
        <a-dropdown>
          <a-menu slot="overlay" :value="text" @click="changeSta">
            <a-menu-item key="2">
              停车中
            </a-menu-item>
            <a-menu-item key="0">
              结束订单
            </a-menu-item>
          </a-menu>
          <a> changeStatus <a-icon type="down" /> </a>
        </a-dropdown>
      </span>
      </a-table>
    </a-drawer>
  </div>
</template>

<script>
  const innerColumns = [
    {
      title: '订单编号',
      dataIndex: 'id',
      align:'center'
    },
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
    },
    // {
    //   title: '订单结束时间',
    //   dataIndex: 'endTime',
    //   width:200,
    //   align:'center'
    // },
    {
      title: '停车费',
      dataIndex: 'fee',
      align:'center'
    },{
      title: '订单状态',
      dataIndex: 'status',
      align:'center'
    },
    {
      title: 'Action',
      dataIndex: 'operation',
      key: 'operation',
      scopedSlots: { customRender: 'operation' },
      align:'center'
    },
  ];
  export default {
    name: "orderSpace",
    data(){
      return {
        rowSpace:{},
        innerColumns,
        innerData:[],
        visible3:false,
        visible2:false,
        visible:false,
        title: '',
        parkingLot:[],
        nowParkingLot: {},
        parkingSpace:[],
        lotSpace:[],
        lotSpaceLength:0,
        userId: this.$cookies.get("uid"),
        space: {
          parkingSpaceId:0,
          parkingLotId:0,
          status:0
        },
        user:this.$cookies.get("user"),
        flag: true,
        time1:null,
        // moment2
      }
    },
    methods:{
      //修改车位状态和订单状态
      changeSta(record){
        console.log(this.rowSpace)
        this.$axios.put('/api/user/changeSta',{
          spaceStatus:record.key,
          spaceId:this.rowSpace.parkingSpaceId,
          id:this.rowSpace.id,
          createTime:this.rowSpace.createTime,
          userId:this.$cookies.get("uid")
        }).then(res=>{
          if(res.data.data === null || res.data.data === ''){
            this.$message.warn("车位状态修改失败！！！")
            return
          }
          this.$message.success("车位状态修改成功！！！")
          this.selectMyorderList()
          this.visible3=true
        })
      },
      // 选择想要预定的停车位
      selectChange(id){
       this.space.parkingSpaceId=id
      },
      // 查询停车场剩余车位
      async order(item){
        this.nowParkingLot=item
        this.space.parkingLotId=item.parkingLotId
        await this.$axios.get("api/user/selectParkingSpace",{params:{lotId:this.space.parkingLotId}}).then(res=>{
          this.parkingSpace=res.data.data
          console.log(this.parkingSpace)
          this.lotSpace=this.parkingSpace.filter(i => item.parkingLotId === i.parkingLotId  )
          this.lotSpaceLength=this.lotSpace.length
          if(this.lotSpaceLength !== 0){
            this.flag=false
          }else {
            this.flag=true
          }
        })
      },
      // 查询城市停车场数量
      selectParkingLot(){
        this.$axios.get("api/user/selectParkingLot").then(res=>{
          this.parkingLot=res.data.data
          this.title="目前共有"+this.parkingLot.length+"个城市停车场"
        })
      },
      // 抽屉状态改变后执行
      afterVisibleChange(val) {
        console.log('visible', val);
      },
      showDrawer() {
        this.visible = true;
        this.$message.info("请先选择停车场，再选择停车位")
      },
      showDrawer2() {
        this.selectMyorderList()
        this.visible3 = true;
      },
      // 抽屉，点击其他位置触发
      onClose() {
        this.visible = false;
        this.visible3 = false;
      },
      //锁定停车位，status 0-->1
      async lockSpcae(){
        this.space.status=1
        await this.$axios({
          url:'/api/user/updateSpaceStatus',
          method:"put",
          data:this.space
        }).then(res=>{
          if (res.data.data){
            this.$message.success("预定成功")
          }
        })
      },
      // 选择停车位进行预定，创建相应订单
      async orderCreate(){
        await this.$axios.post('/api/user/createOrder',{user:this.user,parkingSpace:this.space}).then(res=>{
          if(res.data.data === null || res.data.data === ''){
            this.$message.warn("订单创建失败！请选择其他停车位")
            this.visible2=false
            this.visible=false
            return
          }else {
            this.lockSpcae()
            this.selectMyorderList()
            this.visible2=false
            this.visible=false
            var myDate = this.getNowFormatDate()
            console.log(myDate)

            // var date = new Date();
            // var time1 = date.toLocaleTimeString();
            // console.log(date)
            var spaceId =this.space.parkingSpaceId
            var orderId=res.data.data
            //订单创建成功后倒计时十五分钟再查看车库状态是否变更，未变更则自动完成订单即订单状态 1-->0 车位状态 1-->0
            var time = window.setTimeout(()=>{
              this.$axios.get("api/user/selectOrderSpace",{params:{spaceId:spaceId}}).then(res=> {
                  if(res.data.data == 1){

                    this.$axios.put('/api/user/changeSta',{
                      spaceStatus: '0',
                      spaceId:this.space.parkingSpaceId,
                      id: orderId,
                      createTime:myDate,
                      userId:this.$cookies.get("uid")
                    }).then(res=>{
                      if(res.data.data === null || res.data.data === ''){
                        this.$message.warn("车位状态修改失败！！！")
                        return
                      }
                      this.$message.warn("15分钟未到指定地点，已取消订单")
                      this.selectMyorderList()
                    })
                  }
              })
              // window.clearTimeout(time) //去除定时器

            },1000*60*15)
          }
        })
      },
      //查询订单状态
      selectMyorderList(){
         this.$axios.get('/api/user/selectMyorderList',{params:{userId:this.userId}}).then(res=>{
            this.innerData=res.data.data.filter(item => item.status==1 || item.status==2)
          for(var i=0;i<this.innerData.length;i++) {
            if (this.innerData[i].status == 1) {
              this.innerData[i].status = '已预订'
            }
            if (this.innerData[i].status == 2) {
              this.innerData[i].status = '停车中'
            }
          }
        })
      },
      getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        var time = date.toLocaleTimeString();
        console.log(time)
        if (month >= 1 && month <= 9) {
          month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
          strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate+' '+time;
        return currentdate;
      }

    },
    created() {
      this.selectParkingLot()
      this.selectMyorderList()
    }
  }
</script>

<style scoped>

</style>
