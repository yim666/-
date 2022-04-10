<template>
  <div >
    <a-row >
          <a-descriptions bordered title="个人信息" style="margin-left: 10%;margin-right: 10%">
            <a-descriptions-item  label="用户名" span="1">
              {{user.userName}}
            </a-descriptions-item>
            <a-descriptions-item label="账号">
              {{user.userId}}
            </a-descriptions-item>
            <a-descriptions-item label="邮箱">
              {{user.email}}
            </a-descriptions-item>
            <a-descriptions-item label="手机号">
              {{user.phone}}
            </a-descriptions-item>
            <a-descriptions-item label="车牌号">
              {{user.carId}}
            </a-descriptions-item>
          </a-descriptions>
    </a-row>
    <a-row style="padding-top: 5%">
      <a-button >修改个人信息</a-button>

    </a-row>

  </div>
</template>

<script>
  export default {
    name: "info",
    data(){
      return{
        user:this.$cookies.get("user")
      }
    },
    methods:{

    },
    mounted() {
      console.log(this.user)
    }
  }
</script>

<style scoped>
  .back{
    height: 100%;
    width: 100%;
    /*position: absolute;*/
    background-color: #42b983;
  }
</style>
