<template>
  <div>
    <a-input-group compact style="width: 400px;margin-left: 35%">
      <a-input v-model="addLotName" placeholder="请输入停车场名称" style="width: calc(100% - 200px)" />
      <a-button type="primary" @click="addLot">addLot</a-button>
    </a-input-group>
  <a-table :columns="columns" :data-source="data" style="margin-right:15%;margin-left:15% "
           :rowKey="record => record.ParkingLotId" @expand="expand"  expandRowByClick
           @expandedRowsChange="expandedRowsChange"
           :expandedRowKeys="expandedRowKeys"
           :pagination="false">
    <span slot="operation" slot-scope="text, record, index" >
      <a-button type="primary" size="small" @click="addSpace(record)">
       addSpace
      </a-button>
      <a-button type="danger" size="small" @click="deleteLot(record)">
       deleteLot
      </a-button>
    </span>
<!--    <a slot="operation" slot-scope="text, record, index" @click="addSpace(record) ">delete</a>-->
    <a-table
      slot="expandedRowRender"
      slot-scope="text"
      :columns="innerColumns"
      :data-source="innerData"
      :pagination="pagination"
    >
      <span slot="operation" slot-scope="text, record, index" @mouseover="()=>rowSpace=record">
        <a-dropdown>
          <a-menu slot="overlay" :value="text" @click="changeSta">
            <a-menu-item key="0">
              空闲
            </a-menu-item>
            <a-menu-item key="1">
              已预订
            </a-menu-item>
            <a-menu-item key="2">
              停车中
            </a-menu-item>
          </a-menu>
          <a> changeStatus <a-icon type="down" /> </a>
        </a-dropdown>
        <a-col :span="4"></a-col>
        <a-popconfirm title="确定要删除这个停车位吗？？？" ok-text="Yes" cancel-text="No" @confirm="deleteSpace">
        <a-button type="danger" size="small" >
        Delete
        </a-button>
        </a-popconfirm>

      </span>
    </a-table>
  </a-table>
  </div>
</template>

<script>
  const columns = [
    { title: '停车场编号', dataIndex: 'parkingLotId', key: 'parkingLotId' },
    { title: '停车场名称', dataIndex: 'parkingLotName', key: 'parkingLotName' },
    { title: '停车场使用情况', dataIndex: 'status', key: 'status' },
    { title: 'Action', key: 'operation', scopedSlots: { customRender: 'operation' } },
  ];

  const innerColumns = [
    { title: '所属停车场', dataIndex: 'parkingLotId', key: 'parkingLotId' ,align:'center'},
    { title: '停车位编号', dataIndex: 'parkingSpaceId', key: 'parkingSpaceId' ,align:'center'},
    { title: '停车位使用情况', dataIndex: 'status', key: 'status',align:'center' },
    {
      title: 'Action',
      dataIndex: 'operation',
      key: 'operation',
      scopedSlots: { customRender: 'operation' },
      align:'center'
    },
  ];

  export default {
    name: "lotManage",
    data() {
      return {
        data:[],
        columns,
        innerColumns,
        innerData:[],
        expandedRowKeys:[],
        rowSpace:{},
        pagination:{
          defaultPageSize:3
        },
        addLotName:''
      }
    },
    methods:{
      addLot(){
        this.$axios.post('/api/admin/addLot',{lotName:this.addLotName}).then(res=>{
          if(res.data.data === null || res.data.data === ''){
            this.$message.warn("增加停车场失败！！！")
            return
          }
          this.selectParkingLot()
          this.$message.success("增加停车场成功！！！")
          this.addLotName=''
        })
      },
      async deleteSpace(){
        console.log(this.rowSpace)
       await this.$axios.delete("/api/admin/deleteSpace",{
          params:{
            spaceId:this.rowSpace.parkingSpaceId
          }
        }).then(res=>{
          if(res.data.data === null || res.data.data === ''){
            this.$message.warn("车位删除失败！！！")
            return
          }
          this.expandedRowKeys=[]
          this.$message.success("车位删除成功！！！")
          this.rowSpace={}
        })
      },
      changeSta(record){
        console.log(record)
        console.log(this.rowSpace)
        this.$axios.put('/api/admin/changeSta',{
          spaceStatus:record.key,
          spaceId:this.rowSpace.parkingSpaceId
        }).then(res=>{
          if(res.data.data === null || res.data.data === ''){
            this.$message.warn("车位状态修改失败！！！")
            return
          }
          this.expandedRowKeys=[]
          this.$message.success("车位状态修改成功！！！")
          this.rowSpace={}
        })
      },
      addSpace(record){
        console.log(this.status2)
        // var lotId=record.parkingLotId
        this.$axios.post('/api/admin/addSpace',{lotId:record.parkingLotId}).then(res=>{
          if(res.data.data === null || res.data.data === ''){
            this.$message.warn("增加停车位失败！！！")
            return
          }
          this.selectParkingLot()
          this.$message.success("增加停车位成功！！！")
        })
      },
      deleteLot(record){
        // var lotId=record.parkingLotId
         this.$axios.delete("/api/admin/deleteLot",{
          params:{
            lotId:record.parkingLotId
          }
        }).then(res=>{
          if(res.data.data === null || res.data.data === ''){
            this.$message.warn("停车场删除失败！！！")
            return
          }
          this.expandedRowKeys=[]
          this.$message.success("停车场删除成功！！！")
          this.rowSpace={}
          this.selectParkingLot()
        })
      },
      selectParkingLot() {
        this.$axios.get("api/user/selectParkingLot").then(res=>{
          this.data=res.data.data
          for(var i=0;i<this.data.length;i++) {
            if (this.data[i].status == 0) {
              this.data[i].status = '停车场使用中'
            }
          }
        })
      },
      expand(expanded,record){
        if(expanded){
           this.$axios.get("api/admin/selectParkingSpace",{params:{lotId:record.parkingLotId}}).then(res=>{
             this.innerData=res.data.data
             for(var i=0;i<this.innerData.length;i++) {
               if (this.innerData[i].status == 0) {
                 this.innerData[i].status = '空闲'
               }
               if (this.innerData[i].status == 1) {
                 this.innerData[i].status = '已预订'
               }
               if (this.innerData[i].status == 2) {
                 this.innerData[i].status = '停车中'
               }
             }
           })
        }
      },
      expandedRowsChange (expandedRows) {
        //只能打开一个内部table ,打开第二个时，上一个自动关闭
        if (expandedRows.length >1){
          this.expandedRowKeys=[]
          this.expandedRowKeys = [expandedRows[1]]
        }else {
          this.expandedRowKeys=expandedRows
        }
      }
    },
    created() {
      this.selectParkingLot()
    }
  }
</script>

<style scoped>

</style>
