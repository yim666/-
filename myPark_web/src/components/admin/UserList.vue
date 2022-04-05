<template>
  <div>
  <a-table :columns="columns" :data-source="this.userList" class="mar" :pagination="{pageSize:10}" >
    <a slot="userId" slot-scope="text">{{ text }}</a>
    <span slot="customTitle"><a-icon type="smile-o" /> 用户ID</span>
<!--    行操作-->
    <template
      v-for="col in ['userName', 'email', 'phone','carId']"
      :slot="col"
      slot-scope="text, record, index"
    >
      <div :key="col">
        <a-input
          v-if="record.editable"
          style="margin: -5px 0"
          :value="text"
          @change="e => handleChange(e.target.value, record.userId, col)"
        />
        <template v-else>
          {{ text }}
        </template>
      </div>
    </template>
    <template slot="action" slot-scope="text, record, index">
      <div >
        <span v-if="record.editable">
          <a @click="() => save(record.userId)">Save</a>
          <a-popconfirm title="Sure to cancel?" @confirm="() => cancel(record.userId)">
            <a>Cancel</a>
          </a-popconfirm>
        </span>
        <span v-else>
          <a :disabled="editingKey !== ''" @click="() => edit(record.userId)">Edit</a>
        </span>
      </div>
    </template>
  </a-table>
  </div>
</template>
<script>
  const columns = [
    {
      dataIndex: 'userId',
      key: 'userId',
      slots: { title: 'customTitle' },
      scopedSlots: { customRender: 'userId' },
    },
    {
      title: '用户名',
      dataIndex: 'userName',
      scopedSlots: { customRender: 'userName' },
    },
    {
      title: '用户邮箱',
      dataIndex: 'email',
      scopedSlots: { customRender: 'email' },

    },
    {
      title: '用户手机号',
      dataIndex: 'phone',
      scopedSlots: { customRender: 'phone' },
    },
    {
      title: '用户车牌号',
      dataIndex: 'carId',
      scopedSlots: { customRender: 'carId' },
    },
    {
      title: 'Action',
      scopedSlots: { customRender: 'action' },
    }
  ];
  const userList=[];
  export default {
    name:"userList",
    data() {
      return {
        columns,
        userList,
        cacheData:[],
        editingKey: '',
        target:{}
      };
    },
    methods:{
      selcetUserList(){
        this.$axios.get('/api/admin/selectUserList').then(res=>{
          this.userList=res.data.data
        })
      },
      handleChange(value, key, column) {
        if (this.target.editable){
          this.$set(this.target,column,value)
        }
      },
      edit(key) {
        this.cacheData =this.userList.map(item => ({ ...item }))
        this.target = this.userList.find(item => key === item.userId)
        this.editingKey = key
        if(this.target){
          this.$set(this.target,'editable',true)
        }
      },
       save(key) {
        let i=0
        //取target是userlist的第几条数据  key 是 userId
        for(; i<this.userList.length;i++){
          if(this.userList[i].userId === key) {
            break
          }
        }
        this.$delete(this.target,'editable')
         this.$axios({
           url:'/api/admin/updateUserList',
           method:"put",
           data:this.target
         }).then(res=>{
          if (res.data.data){
            this.$message.success("修改成功")
          }
        })
        this.$set(this.target,'editable',false)
        // this.userList[i]=this.target
        this.editingKey = ''
      },
      cancel(key) {
        this.editingKey = ''
        this.target.editable =false
        this.userList = this.cacheData.map(item => ({ ...item }))
      },
    },
    created() {
      this.selcetUserList()
    }
  };
</script>

<style scoped>
.mar{
  margin-left: 10%;
  margin-right: 10%;
}
</style>
