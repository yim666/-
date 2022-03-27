<template>
  <section >
    <div >
      <i ></i><span>很高兴遇见你,{{user.userName}} 车主</span>
    </div>
    <div >
      <p >教务公告：</p>
      <ul>
        <li @click="openMsg()"><i ></i>清明节放假通知</li>
        <li @click="openMsg()"><i ></i>下周例行工作报告会</li>
        <li @click="openMsg()"><i ></i>关于毕业生答辩具体流程</li>
      </ul>
    </div>
  </section>
</template>

<script>
  export default {
    name:'notice',
    data() {
      return {
        user: this.$cookies.get("User")
      }
    },
    created() {
      this.getUserInfo()
    },
    methods: {
      getUserInfo() { //获取用户信息
        let userName = this.$cookies.get("cname")
        let userId = this.$cookies.get("cid")
        this.user.userName = userName
        this.user.userId = userId
      },
      openMsg() {
        this.$alert('根据《国务院办公厅关于2019年部分节假日安排的通知》精神，越城区行政服务中心将于4月5日（星期五）至4月7日（星期天）进行清明节放假调休，共3天，放假期间不受理业务。4月8日（星期一）开始正常上班受理业务。望市民朋友相互转告，给您带来不便，敬请谅解。','清明节放假通知',{
          confirmButtonText: '确定'
        })
      }
    }
  }
</script>


<style  scoped>

</style>

