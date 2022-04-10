<template>
  <div>
<!--    查看当前车位情况-->
      <a-button type="primary" @click="showDrawer">
        查看车位
      </a-button>
      <a-drawer
        title="车位情况"
        placement="right"
        :closable="false"
        :visible="visible"
        :width="500"
        :after-visible-change="afterVisibleChange"
        @close="onClose"
      >
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

        <a-modal title="温馨提示" :visible="visible2" @cancel="()=>(visible2=false)" @ok="modalok"
        okText="确认预定" cancelText="我再想想">
          <p style="color: blue">预定成功后，请在十五分钟内变更订单状态为停车中，否则将自动取消订单并释放车位</p>
          <h3 style="color: red ">是否继续预定？</h3>
        </a-modal>
      </a-drawer>
  </div>
</template>

<script>
  import moment from 'moment';
  export default {
    name: "orderSpace",
    data(){
      return {
        visible2:false,
        visible:false,
        title: '',
        parkingLot:[],
        nowParkingLot: {},
        parkingSpace:[],
        lotSpace:[],
        lotSpaceLength:0,
        userId: this.$cookies.get("cid"),
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
      selectChange(id){
       this.space.parkingSpaceId=id
      },
      modalok(){
        this.lockSpcae()
        this.orderCreate()
        this.visible2=false
        this.visible=false

      },
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
      selectParkingLot(){
        this.$axios.get("api/user/selectParkingLot").then(res=>{
          this.parkingLot=res.data.data
          this.title="目前共有"+this.parkingLot.length+"个城市停车场"
        })
      },
      afterVisibleChange(val) {
        console.log('visible', val);
      },
      showDrawer() {
        this.visible = true;
        this.$message.info("请先选择停车场，再选择停车位")
      },
      // 点击其他位置触发
      onClose() {
        this.visible = false;
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
      async orderCreate(){
        await this.$axios.post('/api/user/createOrder',{user:this.user,parkingSpace:this.space}).then(res=>{
          if(res.data.data === null || res.data.data === ''){
            this.$message.warn("订单创建失败！！！")
            return
          }else {
            //订单创建成功后倒计时十五分钟再查看车库状态是否变更，未变更则自动完成订单即订单状态 0-->1 车位状态 1-->0
            setInterval(()=>{
              console.log("取消订单")
            },1000*60*15)
          }
        })
      },
      //查询订单状态
      // async selectOrderStatus(){
      //   await this.$axios.get('/api/user/selectOrderStatus',{params:{lotId:this.space.parkingSpaceId}}).then(res=>{
      //
      //   })
      // }

    },
    created() {
      this.selectParkingLot()
    }
  }
</script>

<style scoped>

</style>
