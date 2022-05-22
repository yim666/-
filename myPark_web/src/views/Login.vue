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
      <a  @click="forgetPass">
        Forgot password
      </a>
      <br/>
      <a-button type="primary" html-type="submit" >
        Log in
      </a-button>

      Or

      <a @click="()=>this.visible=true">
        register now!
      </a>
    </a-form-item>
  </a-form>

    <a-modal
      title="注册账号"
      :visible="visible"
      @cancel="handleCancel"
      footer=''
    >
      <a-form :form="registerform" :label-col="{ span: 7 }" :wrapper-col="{ span: 12 }" @submit="registerSubmit">
        <a-form-item label="用户名">
          <a-input
            allow-clear
            v-decorator=" ['userName',
            {
            rules: [{ required: true, message: 'Please input your userName!' }]
            }
            ]"
          />
        </a-form-item>


        <a-form-item label="密码">
          <a-input-password
            allow-clear
            v-decorator=" ['password',
            {
            rules: [{ required: true, message: 'Please input your userName!' }]
            }
            ]"
          />
        </a-form-item>
        <a-form-item label="邮箱">
          <a-input
            allow-clear
            v-decorator=" ['email',
            {
            rules: [{ required: true, message: 'Please input your email!' },
            {pattern:/^\w+@[a-z0-9]+.[a-z]{2,4}$/,
            message: '请输入正确的邮箱！'}]
            }
            ]"
          />
        </a-form-item>
        <a-form-item label="手机号">
          <a-input
            allow-clear
            v-decorator=" ['phone',
            {
            rules: [{ required: true, message: 'Please input your phone!' },
            { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号码!'}]
            }
            ]"
          />
        </a-form-item>
        <a-form-item label="车牌号">
          <a-input
            allow-clear
            v-decorator=" ['carId',
            {
            rules: [{ required: true, message: 'Please input your carId!' },
            {
              pattern:/^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/,
              message: '请输入正确的车牌号！'
            }]
            }
            ]"
          />
        </a-form-item>
        <a-form-item :wrapper-col="{ span: 12, offset: 9 }">
          <a-button type="primary" html-type="submit">
            注册
          </a-button>
        </a-form-item>
      </a-form>
    </a-modal>


  </div>
</template>

<script>
  export default {
    name: "login",
    beforeCreate() {
      this.form = this.$form.createForm(this, { name: 'normal_login' });
      this.registerform = this.$form.createForm(this, { name: 'normal_register' });
    },
    data(){
      return{
        visible:false,
        visible2:false,
        // value:{}
      }
    },
    methods: {
      handleCancel(e) {
        this.visible = false;
        this.visible2 = false;
      },
      registerSubmit(e) {
        //防止意外提交
        e.preventDefault();
        console.log(this.registerform)
        this.registerform.validateFields((err, values) => {
          if (!err) {
            console.log('Received values of form: ', values);
            this.$axios({
              url:'/api/user/CreateUser',
              method:"post",
              data:values
            }).then(res=>{
              if (res.data.data){
                this.$message.success("注册成功，账号已发送到您的邮箱")
              }
            })
          }
          this.visible = false;
        });
      },
      forgetPass(){
        this.form.validateFields((err, values) => {
          console.log(err)
          console.log(values)
          if (values.userName ==undefined||values.userName=='') {
            this.$message.error("请输入账号以发送邮件找回密码")
            return
          }
          {
            this.$axios.get('/api/user/forgetPassWord', {params: {id: values.userName}}).then(res => {debugger
              console.log(res)
              this.$message.success("您的密码已找回，请在邮箱" + res.data.data + "查收")
            })
          }
        })
      },
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
                    this.$cookies.set("aname",resData.adminName)
                    this.$cookies.set("aid",resData.adminId)
                    this.$cookies.set("admin",resData)
                    this.$cookies.set("arole",1)
                    this.$router.push({path:'/admin/index'})
                    break
                  case 2: //用户
                    this.$cookies.set("uname",resData.userName)
                    this.$cookies.set("uid",resData.userId)
                    this.$cookies.set("user",resData)
                    this.$cookies.set("urole",2)
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

