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
            <a-descriptions-item label="余额(元)">
              <a-col :span="16">{{user.money}}元 </a-col>
              <a-col :span="8">
                <a-button  type="primary" @click="()=>visible2=true"  shape="circle">
                  <a-icon type="alipay" /></a-button>
                <a-button style="background-color: #04BE02 " @click="()=>visible3=true"  shape="circle">
                  <a-icon type="wechat" /></a-button>
              </a-col>


            </a-descriptions-item>
          </a-descriptions>
    </a-row>
    <a-row style="padding-top: 5%">
      <a-button type="primary" @click="()=>visible=true" icon="edit">修改个人信息</a-button>

    </a-row>

    <a-modal
      title="编辑个人信息"
      :visible="visible"
      @cancel="handleCancel"
      footer=''
    >
      <a-form :form="form" :label-col="{ span: 7 }" :wrapper-col="{ span: 12 }" @submit="handleSubmit">
        <a-form-item label="用户名">
          <a-input
            allow-clear
            v-decorator=" ['userName',
            { initialValue:user.userName,
            rules: [{ required: true, message: 'Please input your userName!' }]
            }
            ]"
          />
        </a-form-item>

        <a-form-item label="userId">
          <a-input v-decorator=" ['userId',
          { initialValue:user.userId,
          rules: [{ required: true }],
          }
          ]"
          disabled
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
            { initialValue:user.email,
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
            { initialValue:user.phone,
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
            { initialValue:user.carId,
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
            确定更改
          </a-button>
        </a-form-item>
      </a-form>
    </a-modal>

    <a-modal
      title="充值二维码"
      :visible="visible2"
      @cancel="handleCancel"
      footer=''
    >
    </a-modal>
    <a-modal
      title="充值二维码"
      :visible="visible3"
      @cancel="handleCancel"
      footer=''
    >
    </a-modal>
  </div>
</template>

<script>
  export default {
    name: "info",
    beforeCreate() {
      this.form = this.$form.createForm(this, { name: 'normal_login' });
    },
    data(){
      return{
        userId:this.$cookies.get("uid"),
        user:{},
        visible:false,
        visible2:false,
        visible3:false,
        confirmLoading:false,
        formLayout: 'horizontal',
        // form:{}
      }
    },
    methods:{
        selectUser(){
          this.$axios.get('/api/user/selectUser',{params:{id:this.userId}}).then(res=>{
              this.user=res.data.data
          })
        },
      handleCancel(e) {
        this.visible = false;
        this.visible2 = false;
        this.visible3= false;
      },
      handleSubmit(e) {
          //防止意外提交
        e.preventDefault();
        this.form.validateFields((err, values) => {
          if (!err) {
            console.log('Received values of form: ', values);
             this.$axios({
              url:'/api/user/updateUser',
              method:"put",
              data:values
            }).then(res=>{
              if (res.data.data){
                this.$message.success("更改成功")
                this.selectUser()
              }
            })
          }
          this.visible = false;
        });
      },
    },
    created() {
      this.selectUser()
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
