<template>
  <div class="roles">
    <!-- 查询和添加 -->
    <!-- <div class="sys-research">
      <el-row class="row-search">
        <el-col :span="8">
          <el-input
            placeholder="请输入搜索内容"
            v-model="searchText"
            class="input-with-select">
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="searchOlds"></el-button>
          </el-input>
        </el-col>
        <el-col :span="8">
        <div class="block">
        <el-date-picker
        v-model="value6"
		value-format="yyyy-MM-dd"
        type="daterange"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期">
        </el-date-picker>
        </div>
        </el-col>
		<el-badge :value="this.rateCount" class="item">
		  <el-button size="small">心率不合格</el-button>
		</el-badge>
		<el-badge :value="this.preCount" class="item" style="margin-left: 20px;">
			<el-button size="small">血压不合格</el-button>
		</el-badge>
      </el-row>
    </div> -->
    <!-- start 添加角色弹窗 -->
    <!-- <el-dialog
      title="角色添加"
      :visible.sync="roleDialogVisible"
      @close="closeRoleAddDialog"
      width="30%">
      <el-form label-width="80px" :model="roleForm"  ref="addRoleFormRef">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="roleForm.roleName"></el-input>
        </el-form-item>
        <el-form-item label="角色描述" prop="roleDesc">
          <el-input v-model="roleForm.roleDesc"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="roleDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="addRoles('addRoleFormRef')">确 定</el-button>
    </span>
    </el-dialog> -->
    <!-- end 添加角色弹窗 -->

    <!-- 表格 -->
    <el-table :data="oldsList"
              style="width: 100%" border>
      <!-- 可展开行 -->

      <el-table-column type="index" width="150" align="center"></el-table-column>
      <el-table-column prop="name" label="姓名" width="180" align="center"></el-table-column>
      <el-table-column prop="sex" label="性别" width="270" align="center"></el-table-column>
      <el-table-column prop="preState" label="血压状态" width="180" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.preState==1" style="color:red">异常</span>
          <span v-else style="color: #37B328">正常</span>
        </template>
      </el-table-column>
      <el-table-column prop="rateState" label="心率状态" width="180" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.rateState==1" style="color:red">异常</span>
          <span v-else style="color: #37B328">正常</span>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="时间" width="180" align="center"></el-table-column>
      <!--      <el-table-column label="操作" align="center">-->
      <!--        <template slot-scope="scope">-->
      <!--          -->
      <!--          <el-button size="mini" plain type="success" icon="el-icon-check" @click="showRoleDialog(scope.row)">-->
      <!--            详细情况-->
      <!--          </el-button>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
    </el-table>
    <!-- start 分页 -->
    <!-- <div style="margin-top: 10px;text-align: center">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="changePage"
        :current-page.sync="currentPage"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div> -->
    <!-- end 分页 -->
    <!-- 分配权限对话框 -->
    <!--111    <el-dialog title="心率血压详细信息" :visible.sync="isShowRoleDialog" >-->
    <!--
      Tree 树形控件
        :data="data2" 数据源，数据中通过 label 属性表示树形节点的名称；通过 children 属性来指定该节点的子节点
        show-checkbox 添加该属性后，节点会带有复选框
        :default-expanded-keys="[2, 3]" 设置默认展开的节点key（key对应数据中的id）
        :default-checked-keys="[5]" 设置默认选中的节点key（id）
        node-key="id" 使用数据中的哪个属性来表示每个节点的key值，一般就是唯一的id值
        :props="defaultProps"
        :default-expand-all="true" 是否默认展开所有节点
    -->


    <!--start echarts表格-->
    <!--111	  <div id="myChart" ref="mychart" style="width: 500px;height:200px;display: inline-block;"></div>-->
    <!--end echarts表格-->

    <!--start echarts表格-->
    <!--111		<div id="myChartTwo" ref="mychartTwo" style="width: 500px;height:200px;display: inline-block;"></div>-->
    <!--end echarts表格-->

    <!-- 1     <div slot="footer" class="dialog-footer">-->
    <!-- 1       <el-button @click="isShowRoleDialog = false">取 消</el-button>-->
    <!-- 1       <el-button type="primary" @click="assignRights">确 定</el-button>-->
    <!-- 1     </div>-->
    <!-- 1   </el-dialog>-->
  </div>

</template>

<script>
// 引入
import axios from 'axios'

import echarts from 'echarts'

export default {
  data () {
    return {
      searchText: '', // 角色查询内容
      roleDialogVisible: false,
      roleEditDialogVisible: false,
      roleForm: {
        roleName: '',
        roleDesc: ''
      },
      roleEditForm: {
        roleId: '',
        roleName: '',
        roleDesc: ''
      },
      value6: '',
      popoverRole: true,
      total: 0,
      pageSize: 10,
      currentPage: 1,
      oldsList: [{
        oldId:'1',
        name:'小黄',
        sex:'男',
        preState:'正常',
        rateState:'正常',
        createTime:'2019-02-20 15:34:56'
      }],
      oldId: '',
      // 控制分配权限对话框的展示和隐藏
      isShowRoleDialog: false,

      // 树形控件的数据
      rightsTree: [],

      // Tree树形控件，默认使用数据中的 label 属性作为节点名称，使用 children 属性指定该接待你的子节点
      // 但是，如果我们自己的数据结构与默认的结构不同，可以通过 defaultProps 来修改默认的两个属性
      defaultProps: {
        // children: 'children',
        label: 'authName'
      },
      // 当前被分配权限的角色id
      roleId: -1,
      hrs: [],
      bps: [],
      createTimes: [],
      time: '',
      name: '',
      rateCount: '',
      preCount: ''
    }
  },
  created () {
    // let myChart = this.$echarts.init(document.getElementById('myChart'))
    console.log('---')

    // this.getCount()
    this.oldId=localStorage.getItem('oldIdfamily')
    console.log(localStorage.getItem('oldIdfamily'))
    this.searchOlds()
    console.log('---')
    // this.drawLine()
  },
  updated() {
    // this.drawLine()
    // this.getAttendanceData();
  },
  mounted(){
    // this.drawLine()
  },
  methods: {
    // 查询
    searchOlds () {
      console.log('查询老人')
//       console.log('search')
//
      var _this = this
      var oldId = _this.oldId
      console.log(_this.oldId)
// 			console.log(this.value6)
// 			console.log('55555555555555')
// 			if(this.value6.length==0){
// 				console.log('null')
// 				this.value6=['','']
// 			}
// 			console.log(this.value6[1])
      axios.post('restful/ratepre/selectRatepreFamily',
        {
          "oldId":oldId
        },
        {
          headers: {
            'content-type': 'application/json'
          },
          withCredentials: true
        }).then(function (response) {
        console.log(response)
        console.log('====================================')
        _this.oldsList = response.data.ratepreDtoList
        _this.total = response.data.ratepreDtoList.length
      })
        .catch(function (error) {
          console.log(error)
        })
    },
    getCount(){
      console.log('kaishe')
      var _this=this
      axios.post('restful/ratepre/selectRatepreCount',
        {

        },
        {
          headers: {
            'content-type': 'application/json'
          },
          withCredentials: true
        }).then(function (response) {
        console.log("rateCount",response)
        console.log('pppppppp')
        _this.rateCount=response.data.rates.length
        _this.preCount=response.data.pres.length
      })
        .catch(function (error) {
          console.log(error)
        })
    },
    // 添加角色
    addRoles () {
      var _this = this
      axios.post('restful/roles/save',
        {
          'roleName': _this.roleForm.roleName
        },
        {
          headers: {
            'content-type': 'application/json'
          },
          withCredentials: true
        }).then(function (response) {
        console.log(response)
        if (response.data.returnCode === '1111') {
          _this.roleDialogVisible = false
          _this.searchOlds()
          _this.$message({
            type: 'success',
            message: '添加角色成功'
          })
        } else {
          _this.$message({
            type: 'error',
            message: '添加角色失败'
          })
        }
      })
        .catch(function (error) {
          console.log(error)
        })
    },
    // 关闭添加角色对话框
    closeRoleAddDialog () {
      // 重置表单
      this.$refs.addRoleFormRef.resetFields()
    },

    // 删除
    async deleteRole (roleName) {
      console.log(roleName)

      var _this = this
      try {
        await _this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        console.log(roleName)
        axios.post('restful/roles/delete', {'roleName': roleName},
          {
            headers: {
              'content-type': 'application/json'
            },
            withCredentials: true
          }).then(function (response) {
          console.log(response)
          if (response.data.returnCode === '1111') {
            _this.$message({
              type: 'success',
              message: '删除信息成功'
            })
          }
          _this.searchOlds()
        })
          .catch(function (error) {
            console.log(error)
          })
      } catch (err) {
        // 取消删除
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      }
    },
    // 编辑信息
    handleEditRole (index, row) {
      this.roleEditDialogVisible = true
      this.roleEditForm = Object.assign({}, row) // 解决修改弹出框数据时，表格数据会发生同步改变
      // console.log(row)
    },
    handleSizeChange (val) {
      console.log(`每页 ${val} 条`)
      this.pageSize = val
    },
    handleCurrentChange (val) {
      console.log(`当前页: ${val}`)
      this.currentPage = val
    },
    changePage (val) {
      console.log(`当前页: ${val}`)
      this.currentPage = val
    },
    // 展示分配权限对话框
    showRoleDialog (curOld) {
      var _this=this
      var oldId=_this.oldId
      // 展示对话框
      _this.isShowRoleDialog = true
      axios.post('restful/ratepre/selectRatepreDetail',{
          "oldId":oldId
        },
        {
          headers: {
            'content-type': 'application/json'
          },
          withCredentials: true
        }).then(function (response) {

        console.log(response)
        console.log('this',_this)
        _this.bps=response.data.bps
        _this.hrs=response.data.hrs
        _this.time=response.data.time
        _this.createTimes=response.data.createTimes
        _this.drawLine()
        _this.drawLineTwo()
        console.log('bps',_this.bps)
      })
        .catch(function (error) {
          console.log(error)
        })

      // 暂存角色id
      this.roleId = curOld.id
    },

    // 给角色分配权限
    async assignRights () {
    },

    //  echarts
    drawLine(){
      // var echarts = require('echarts');
      // 基于准备好的dom，初始化echarts实例
      var _this=this
      this.$nextTick(()=>{
        let myChart = _this.$echarts.init(document.getElementById('myChart'));
        // let myChart =this.$echarts.init(this.$refs.myChart)
        //         let dom = this.$refs.mychart;
        // 		this.myChart = echarts.init(dom);

        // 绘制图表
        myChart.setOption({
          title: { text: _this.name+"  心率  "+_this.time+"时" },
          tooltip: {},
          xAxis: {
            data: _this.createTimes
          },
          yAxis: {},
          series: [{
            name: '销量',
            type: 'line',
            data: _this.hrs
          }]
        });
      });

    },

    //  echartsTwo
    drawLineTwo(){
      // var echarts = require('echarts');
      var _this=this
      // 基于准备好的dom，初始化echarts实例
      this.$nextTick(()=>{
        let myChart = this.$echarts.init(document.getElementById('myChartTwo'));
        // let myChart =this.$echarts.init(this.$refs.myChart)
        //         let dom = this.$refs.mychart;
        // 		this.myChart = echarts.init(dom);
        // 绘制图表
        myChart.setOption({
          title: { text: _this.name+"  血压  "+_this.time+"时" },
          tooltip: {},
          xAxis: {
            data: _this.createTimes
          },
          yAxis: {},
          series: [{
            name: '销量',
            type: 'line',
            data: _this.bps
          }]
        });
      });

    }
  }
}

</script>

<style lang="less" scoped>
.sys-research {
  height: 60px;
  padding: 20px 20px 0;
}

.level1 {
  border-bottom: 1px dotted #ccc;
  padding: 10px 0;

  &:last-child {
    border: 0;
  }
}

.level2 {
  padding: 5px 0;
}

.level3 {
  margin: 3px;
}

</style>
