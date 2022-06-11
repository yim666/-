<template>
  <div >
    <a-row class="back">
<!--      首行-->
      <a-col :span="4">
        <h2 style="margin-left:20%;margin-top: 10% ;" >{{time}}</h2>
      </a-col>
      <a-col :span="16" >
        <h1  style="font-style:italic;margin-top: 2% ;color: blue">
         城&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;市&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 停 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 车 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 场  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;管&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  理  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;系  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;统
        </h1>
      </a-col>
      <a-col :span="4">
        <a-dropdown style="margin-top: 10%">
          <a-button  shape="circle" size="large"> <a-icon type="setting" theme="filled" spin /></a-button>
          <a-menu slot="overlay">
            <a-menu-item>
              您好，
              <span style="color: red">{{uname}}</span>
            </a-menu-item>
            <a-menu-item >
              <a-popconfirm
                title="确定要退出登录吗?"
                ok-text="Yes"
                cancel-text="No"
                @confirm="confirm"
                @cancel="cancel"
              >
                <a href="#">退出登录</a>
              </a-popconfirm>
            </a-menu-item>
          </a-menu>
        </a-dropdown>
      </a-col>
    </a-row>

    <a-row>
    <a-tabs default-active-key="1" @change="callback" size="large">
      <a-tab-pane key="1" tab="基本信息"  >
        <Info></Info>
      </a-tab-pane>
      <a-tab-pane key="2" tab=" 公告 " force-render>
        <Notice></Notice>
      </a-tab-pane>
      <a-tab-pane key="3" tab="预约车位">
<!--        地图模块 + 弹出框预约-->
<!--        <OrderSpace></OrderSpace>-->
        <MyMap></MyMap>
      </a-tab-pane>
      <a-tab-pane key="4" tab="历史订单">
<!--        查询所有关联用户id的订单，按时间排序-->
        <OrderHistory></OrderHistory>
      </a-tab-pane>
      <a-tab-pane key="5" tab="投诉">
        <Advice></Advice>
      </a-tab-pane>
    </a-tabs>

    </a-row>
  </div>
</template>

<script>
  import Info from "../../components/User/info";
  import Notice from "../../components/User/notice";
  import Advice from "../../components/User/advice";
  import OrderSpace from "../../components/User/orderSpace";
  import OrderHistory from "../../components/User/OrderHistory";
  import MyMap from "../../components/util/myMap";
  export default {
    name: "index",
    components: {Info,Notice,Advice,OrderSpace,OrderHistory,MyMap},
    data(){
      return{
        uname: this.$cookies.get("uname"),
        time: ''
      }
    },
    methods:{
      callback(key) {
        console.log(key);
      },
      confirm(){
        this.$message.success('退出成功');
        this.$router.push({path:'/'})
      },
      cancel(){
        this.$message.error('取消退出');
      },
      thisTime(){
        // 实现局部刷新
        setInterval(()=>{
          var myDate = new Date();
          this.time=myDate.toLocaleString( );
        },1000)
      }
    },
    created() {
      this.thisTime()
    }
  }
</script>

<style scoped>
  .back{
    background-image: url("../../assets/img.png") ;
    color: #42b983;
    width: 100%;
    /*position: absolute;*/
  }
  /*html,body{*/
  /*  height: 100%;*/
  /*}*/
</style>
