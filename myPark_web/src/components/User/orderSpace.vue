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
          <a-popover v-for="item in parkingLot">
            <template slot="content">
              <p>{{item.parkingLotName}}</p>
            </template>
          <a-card-grid style="width:33.33%;text-align:center;color: blue" @click="order(item)">
            {{item.parkingLotName}}
          </a-card-grid>
          </a-popover>
        </a-card>

        <a-time-picker v-model="time1" format="HH:mm" />
<!--        <a-time-picker :default-value="moment2('12:08', 'HH:mm')" format="HH:mm" />-->
        <a-card style="margin-top: 10%">
          <p>{{nowParkingLot.parkingLotName}}还剩{{lotSpaceLength}}个停车位</p>
          <a-button type="primary" :disabled="flag">预定车位</a-button>
        </a-card>

        <a-modal title="投诉建议" :visible="visible2" @cancel="()=>(visible2=false)">
          <p>asd</p>
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
        visible:false,
        title: '',
        parkingLot:[],
        nowParkingLot: {},
        nowLotId: 0,
        parkingSpace:[],
        lotSpace:[],
        lotSpaceLength:0,
        userId: this.$cookies.get("cid"),
        flag: true,
        time1:null,
        // moment2
      }
    },
    methods:{
      async order(item){
        this.nowParkingLot=item
        this.nowLotId=item.parkingLotId
        await this.$axios.get("api/user/selectParkingSpace",{params:{lotId:this.nowLotId}}).then(res=>{
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
      },
      // 点击其他位置触发
      onClose() {
        this.visible = false;
      },
    },
    created() {
      this.selectParkingLot()
    }
  }
</script>

<style scoped>

</style>
