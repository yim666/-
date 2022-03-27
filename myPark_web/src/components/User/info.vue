<template>
  <div style="background:#ECECEC; padding:30px;">
    <a-row style="height: 100%">
      <a-col :span="8">
        <a-card title="个人信息" :bordered="false" >
          <a-input-password/>
          <p>Card content</p>
          <p>Card content</p>
        </a-card>
      </a-col>

      <a-col :span="16">

      </a-col>
    </a-row>

  </div>
</template>

<script>
  export default {
    name: "info",
    data(){
      return{

      }
    },
    methods:{

    }
  }
</script>

<style scoped>

</style>
