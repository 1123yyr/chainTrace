<template>
  <div class="roles">
    
    <!-- start 添加角色弹窗 -->
    <el-dialog
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
    </el-dialog>
    <!-- end 添加角色弹窗 -->
    <!-- start 编辑角色弹窗 -->
    <el-dialog
      title="角色编辑"
      :visible.sync="roleEditDialogVisible"
      width="30%">
      <el-form label-width="80px" :model="roleEditForm">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="roleEditForm.roleName"></el-input>
        </el-form-item>
        <el-form-item label="角色描述" prop="roleDesc">
          <el-input v-model="roleEditForm.roleDesc"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="roleEditDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="editRole">确 定</el-button>
    </span>
    </el-dialog>
    <!-- end 编辑角色弹窗 -->
    <!-- 表格 -->
    <el-table :data="stepList"
    style="width: 100%" border>
    
      <el-table-column type="index" width="100" align="center"></el-table-column>
      <el-table-column prop="name" label="姓名" width="180" align="center"></el-table-column>
      <el-table-column prop="sex" label="性别" width="100" align="center"></el-table-column>
			<el-table-column prop="step" label="今日步数" width="150" align="center"></el-table-column>
			<el-table-column prop="sleep" label="睡眠时间" width="150" align="center"></el-table-column>
			<el-table-column prop="sleepSate" label="睡眠情况" width="150" align="center">
				<template slot-scope="scope">
				  <span v-if="scope.row.sleepSate==1" style="color:red">异常</span>
				  <span v-else style="color: #37B328">正常</span>
				</template>
			</el-table-column>
			<el-table-column prop="createTime" label="时间" width="180" align="center"></el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          
          <el-button size="mini" plain type="success" icon="el-icon-check" @click="showRoleDialog(scope.row)">
            详细情况
          </el-button>
        </template>
      </el-table-column>
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
    <el-dialog title="睡眠时间统计" :visible.sync="isShowRoleDialog">
      
	  
	  <!--start echarts表格-->
	  <div id="myChart" ref="mychart" style="margin-left: 280px;" :style="{width: '300px', height: '300px'}"></div>
		<!--end echarts表格-->
      <div slot="footer" class="dialog-footer">
        <el-button @click="isShowRoleDialog = false">取 消</el-button>
        <el-button type="primary" @click="assignRights">确 定</el-button>
      </div>
    </el-dialog>
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
			value6: '',
      roleEditForm: {
        roleId: '',
        roleName: '',
        roleDesc: ''
      },
      popoverRole: true,
      total: 0,
      pageSize: 10,
      currentPage: 1,
      stepList: [{
		  oldId:'1',
			name:'小黄',
		  sex:'男',
			sleep: '8',
		  step:'1000',
			sleepSate:'正常',
			createTime:'2019-02-20'
	  }],
		sleep: '',
		sleep: [],
		name: '',
		time: '',
		createTime: [],
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
      roleId: -1
    }
  },
  created () {
	  // let myChart = this.$echarts.init(document.getElementById('myChart'))
		this.oldId=localStorage.getItem('oldIdfamily')
		console.log(localStorage.getItem('oldIdfamily'))
    this.searchStep()
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
    searchStep () {
      var _this=this
            // curRole 表示当前角色的数据，children属性是当前角色拥有的权限
      //       console.log('showRoleDialog', curOld.name)
      // 			_this.name=curOld.name
      			var oldId=_this.oldId
      axios.post('restful/step/selectStepFamily',
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
        _this.stepList = response.data.stepDtos
        _this.total = response.data.stepDtos.length
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
          _this.searchStep()
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
    // 编辑
    editRole () {

    },
    // 删除
    async deleteRole (roleName) {

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
      			axios.post('restful/step/selectStepDetail',{
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
      				_this.sleep=response.data.sleep
      				_this.createTime=response.data.createTime
      				_this.drawLine()
      				console.log('bps',_this.bps)
      			})
      				.catch(function (error) {
      					console.log(error)
      				})
      
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
			
					// 绘制图表
					myChart.setOption({
					    title: { text: _this.name+"  睡眠时间  "+_this.time },
					    tooltip: {},
					    xAxis: {
					        data: _this.createTime
					    },
					    yAxis: [{
										type: 'value',
												show: true,    //显示纵轴false-不显示，true-显示
												name:'小时'   //这里是纵轴标题
									}],
					    series: [{
					        name: '销量',
					        type: 'bar',
					        data: _this.sleep,
											itemStyle:{
											           normal:{
											                  color:'#99CCFF'
											              }
											          },
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
