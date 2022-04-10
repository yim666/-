<template>
  <div class="back">
  <a-form
    :form="form"
    @submit="handleSubmit"
  >
    <h1 style="color: #42b983;margin: 10%">城市停车场管理系统</h1>
    <a-form-item>
      <a-input
        allow-clear
        style="text-align: center;width: 30%"
        v-decorator="[
          'userName',
          { rules: [{ required: true, message: 'Please input your username!' }] },
        ]"
        placeholder="Username"
      >
        <a-icon slot="prefix" type="user" style="color: rgba(0,0,0,.25)" />
      </a-input>
    </a-form-item>

    <a-form-item>
      <a-input-password
        allow-clear
        style="text-align: center;width: 30%"
        v-decorator="[
          'password',
          { rules: [{ required: true, message: 'Please input your Password!' }] },
        ]"
        type="password"
        placeholder="Password"
      >
        <a-icon slot="prefix" type="lock" style="color: rgba(0,0,0,.25)" />
      </a-input-password>
    </a-form-item>

    <a-form-item>
      <a-checkbox
        v-decorator="[
          'remember',
          {
            valuePropName: 'checked',
            initialValue: true,
          },
        ]"
      >
        Remember me
      </a-checkbox>
      <a  href="https://www.baidu.com">
        Forgot password
      </a>
      <br/>
      <a-button type="primary" html-type="submit" >
        Log in
      </a-button>

      Or

      <a href="">
        register now!
      </a>
    </a-form-item>
  </a-form>
  </div>
</template>

<script>
  export default {
    name: "login",
    beforeCreate() {
      this.form = this.$form.createForm(this, { name: 'normal_login' });
    },
    data(){
      return{
        // value:{}
      }
    },
    methods: {
      handleSubmit(e) {
        e.preventDefault();
        this.form.validateFields((err, values) => {
          if (!err) {
            console.log('Received values of form: ', values);
            this.$axios({
              url: `/api/login`,
              method:'post',
              data:values
              //  get 方法传数据 用params: values 来传
            }).then(res=>{

              let resData = res.data.data
                // this.value = res.data.data
              if(resData !=null){
                //判断用户角色

                switch (resData.role){
                  // case注意区分数字和字符
                  case 1: //管理员
                    this.$cookies.set("cname",resData.adminName)
                    this.$cookies.set("cid",resData.adminId)
                    this.$cookies.set("admin",resData)
                    this.$cookies.set("role",1)
                    this.$router.push({path:'/admin/index'})
                    break
                  case 2: //用户
                    this.$cookies.set("cname",resData.userName)
                    this.$cookies.set("cid",resData.userId)
                    this.$cookies.set("user",resData)
                    this.$cookies.set("role",2)
                    this.$router.push({path:'/user/index'})
                    break
                }
              }else {
                this.$message.error("用户名或密码错误")
              }
            })
            // axios 结束
          }
        });
      },
    },
  };
</script>

<style scoped>
.back{
  background-image: url("../assets/img.png") ;
  height: 100%;
  width: 100%;
  position: absolute;
}
</style>

