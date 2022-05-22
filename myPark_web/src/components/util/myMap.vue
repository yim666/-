<template>
  <div class="home_div">
    <div id="container">
<!--      <input id='tipinput' type="text" style="position: relative;float:left;z-index: 5"/>-->
        <OrderSpace ></OrderSpace>
    </div>
  </div>
</template>

<script>
  import AMapLoader from '@amap/amap-jsapi-loader';

  import OrderSpace from "../User/orderSpace";


  export default {
    name: "myMap",
    components:{OrderSpace},
    data(){
      return{
        map:null,
        marker:[],
        // tipinput:''

      }
    },
    methods:{
      initMap(){
        AMapLoader.load({
          key:"6ca20fe386ae89f2c155f2e44b050969",             // 申请好的Web端开发者Key，首次调用 load 时必填
          version:"2.0",      // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
          plugins:['AMap.ToolBar','AMap.Driving','AMap.Scale'
            ,'AMap.Marker','AMap.Geocoder',
            'AMap.Autocomplete', 'AMap.PlaceSearch',
            'AMap.MouseTool'],       // 需要使用的的插件列表，如比例尺'AMap.Scale'等
        }).then((AMap)=>{
          this.map = new AMap.Map("container",{  //设置地图容器id
            viewMode:"3D",    //是否为3D地图模式
            zoom:13,           //初始化地图级别
            center:[120.680,31.333], //初始化地图中心点位置
          })

          //初始标记地点
          var marker1 = new AMap.Marker({
            //苏州站
            position: new AMap.LngLat(120.612251,31.328999),
            offset: new AMap.Pixel(-10, -10),
            zoom: 8
          })
          var marker2 = new AMap.Marker({
            //苏州中心
            position: new AMap.LngLat(120.6799,31.3159),
            offset: new AMap.Pixel(-10, -10),
            zoom: 8
          })
          var marker3 = new AMap.Marker({
            // 苏大
            position: new AMap.LngLat(120.64250,31.3035),
            offset: new AMap.Pixel(-10, -10),
            zoom: 8
          })
          this.map.add(marker1)
          this.map.add(marker2)
          this.map.add(marker3)

        }).catch(e=>{
          console.log(e);
        })

      },
    },
  mounted(){
    //DOM初始化完成进行地图初始化
    this.initMap();
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
    padding:10px;
    margin: 0px;
    width: 100%;
    height: 600px;
    /*height: 100%;*/
    /*position:absolute;*/
  }
</style>
