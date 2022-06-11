<template>
    <div class="home_div">
      <div id="container">
  <!--      <input id='tipinput' type="text" style="position: relative;float:left;z-index: 5"/>-->
          <OrderSpace ></OrderSpace>

        <a-affix  style="position: fixed;z-index: 50;bottom: 50px;right: 100px">
          <a-tag color="#87d068" ><a @click="openPage">打开地图导航</a></a-tag>
        </a-affix>
      </div>
    </div>


</template>

<script>
  import AMapLoader from '@amap/amap-jsapi-loader'

  import OrderSpace from '../User/orderSpace'

  export default {
    name: 'myMap',
    components: {OrderSpace},
    data () {
      return {
        map: null,
        marker: [],
        // tipinput:''
        positionInfo: {},
      }
    },
    methods: {
      openPage(){
        let url="https://gaode.com/dir"
        window.open(url);
      },
      initMap () {
        AMapLoader.load({
          key: '6ca20fe386ae89f2c155f2e44b050969',             // 申请好的Web端开发者Key，首次调用 load 时必填
          version: '2.0',      // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
          plugins: ['AMap.ToolBar', 'AMap.Driving', 'AMap.Scale'
            , 'AMap.Marker', 'AMap.Geocoder','AMap.Geolocation',
            'AMap.Autocomplete', 'AMap.PlaceSearch',
            'AMap.MouseTool'],       // 需要使用的的插件列表，如比例尺'AMap.Scale'等
        }).then((AMap) => {
          this.map = new AMap.Map('container', {  //设置地图容器id
            viewMode: '3D',    //是否为3D地图模式
            zoom: 13,           //初始化地图级别
            center: [120.632, 31.325], //初始化地图中心点位置
          })

          //初始标记地点
          var marker1 = new AMap.Marker({
            //苏州站
            position: new AMap.LngLat(120.612251, 31.328999),
            offset: new AMap.Pixel(-10, -10),
            zoom: 8
          })
          var marker2 = new AMap.Marker({
            //苏州中心
            position: new AMap.LngLat(120.6799, 31.3159),
            offset: new AMap.Pixel(-10, -10),
            zoom: 8
          })
          var marker3 = new AMap.Marker({
            // 苏大
            position: new AMap.LngLat(120.64250, 31.3035),
            offset: new AMap.Pixel(-10, -10),
            zoom: 8
          })
          this.map.add(marker1)
          this.map.add(marker2)
          this.map.add(marker3)

          //定位当前位置
          // var mapObj = new AMap.Map('iCenter')//以为这里是要获取地图容器的id,所以要写在mounted之中
          var geolocation
          var that = this
          this.map.plugin('AMap.Geolocation',  function () {
            geolocation = new AMap.Geolocation({
              enableHighAccuracy: true, // 是否使用高精度定位，默认:true
              timeout: 10000,           // 超过10秒后停止定位，默认：无穷大
              maximumAge: 0,            // 定位结果缓存0毫秒，默认：0
              convert: true,            // 自动偏移坐标，偏移后的坐标为高德坐标，默认：true
              showButton: true,         // 显示定位按钮，默认：true
              buttonPosition: 'LB',     // 定位按钮停靠位置，默认：'LB'，左下角
              buttonOffset: new AMap.Pixel(10, 20), // 定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
              showMarker: true,         // 定位成功后在定位到的位置显示点标记，默认：true
              showCircle: true,         // 定位成功后用圆圈表示定位精度范围，默认：true
              panToLocation: true,      // 定位成功后将定位到的位置作为地图中心点，默认：true
              zoomToAccuracy: true       // 定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
            })
            this.map.addControl(geolocation)
            geolocation.getCurrentPosition()
            AMap.event.addListener(geolocation, 'complete', (obj)=>{
              this.positionInfo = obj
              var res = '经纬度：' + obj.position + '精度范围：' + obj.accuracy + '米定位结果的来源：' + obj.location_type + '状态信息：' + obj.info + '地址：' + obj.formattedAddress + '地址信息：' + JSON.stringify(obj.addressComponent, null, 4)
              console.log(this.positionInfo)
              console.log(res)
            }) // 返回定位信息

            AMap.event.addListener(geolocation, 'error', (obj)=>{
              alert(obj.info + '--' + obj.message)
              console.log(obj)
            })       // 返回定位出错信息
          })

        }).catch(e => {
          console.log(e)
        })
      },
    },
    created () {
      //DOM初始化完成进行地图初始化
      this.initMap()
    }
  }
</script>

<style scoped>
  /*.home_div{*/
  /*  padding:0px;*/
  /*  margin: 0px;*/
  /*  width: 100%;*/
  /*  height: 100%;*/
  /*  position: relative;*/
  /*}*/
  #container {
    padding: 10px;
    margin-top: -17px;
    /*width: 100%;*/
    /*height: 570px;*/
    width:100%;
    min-height: calc(100vh - 136px);
    display: flex;
    justify-content: center;
  }



</style>
