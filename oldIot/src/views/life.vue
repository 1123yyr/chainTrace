<template>
  <div class="roles">
    <!-- 查询和添加 -->
    <div class="sys-research">
      <el-row class="row-search">
		  
        <el-col :span="8">
          <!-- 查询 -->
          <el-input
            placeholder="请输入搜索内容"
            v-model="searchText"
            class="input-with-select">
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="searchOldPersonLife"></el-button>
          </el-input>
        </el-col>
        
		
      </el-row>
    </div>
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
    <el-table :data="OldPersonList.slice((currentPage-1)*pageSize,currentPage*pageSize)"
    style="width: 100%" border :cell-style="cellStyle">
    <!-- 可展开行 -->
      
      <el-table-column type="index" width="100" align="center"></el-table-column>
      <el-table-column prop="name" label="姓名" width="140" align="center"></el-table-column>
      <el-table-column prop="sex" label="性别" width="140" align="center"></el-table-column>
	  <el-table-column prop="illness" label="病史" width="140" align="center"></el-table-column>
	  <el-table-column prop="warn" label="过敏药物" width="140" align="center"></el-table-column>
	  <el-table-column prop="drag" label="常用药物" width="140" align="center"></el-table-column>
      <el-table-column prop="foodLike" label="喜欢吃的食物" width="auto" align="center"></el-table-column>
	  <el-table-column prop="foodHate" label="忌口" width="auto" align="center"></el-table-column>
    </el-table>
    <!-- start 分页 -->
    <div style="margin-top: 10px;text-align: center">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="changePage"
        :current-page.sync="currentPage"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
    <!-- end 分页 -->
    <!-- 分配权限对话框 -->
    <el-dialog title="角色授权" :visible.sync="isShowRoleDialog">
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
      <el-tree
        ref="tree"
        :data="rightsTree"
        :default-expand-all="true"
        node-key="id"
        show-checkbox
        :props="defaultProps"
      >
      </el-tree>
	  
	  <!--start echarts表格-->
	  <div id="myChart" ref="mychart" :style="{width: '300px', height: '300px'}"></div>
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
      roleEditForm: {
        roleId: '',
        roleName: '',
        roleDesc: ''
      },
      popoverRole: true,
      total: 0,
      pageSize: 10,
      currentPage: 1,
      OldPersonList: [{
		name:'小黄',
		sex:'男',
		illness:'心脏病',
		warn:'板蓝根',
		drag:'定心丸',
		foodLike:'芹菜',
		foodHate:'猪肉'
	  }],
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
    this.searchOldPersonLife()
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
    searchOldPersonLife () {
      var _this = this
      var name = _this.searchText

      axios.post('restful/oldPerson/select',
        {
          'name': name === '' ? '' : _this.searchText
        },
        {
          headers: {
            'content-type': 'application/json'
          },
          withCredentials: true
        }).then(function (response) {
        console.log(response)
        _this.OldPersonList = response.data.oldPeople
        _this.total = response.data.oldPeople.length
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
          _this.searchOldPersonLife()
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
	cellStyle(date){
		if(date.columnIndex == 4){
			return 'color:red'
		}
	},
    // 关闭添加角色对话框
    closeRoleAddDialog () {
      // 重置表单
      this.$refs.addRoleFormRef.resetFields()
    },
    // 编辑
    editRole () {
      var _this = this
      axios.post('restful/roles/update', _this.roleEditForm,
        {
          headers: {
            'content-type': 'application/json'
          },
          withCredentials: true
        }).then(function (response) {
        console.log(response)
        if (response.data.returnCode === '1111') {
          _this.roleEditDialogVisible = false
          _this.$message({
            type: 'success',
            message: '编辑角色信息成功'
          })
          _this.searchOldPersonLife()
        } else {
          _this.$message({
            type: 'error',
            message: '编辑角色信息失败'
          })
        }
      })
        .catch(function (error) {
          console.log(error)
        })
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
          _this.searchOldPersonLife()
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
    showRoleDialog (curRole) {
      // curRole 表示当前角色的数据，children属性是当前角色拥有的权限
      console.log('showRoleDialog', curRole)
      // 展示对话框
      this.isShowRoleDialog = true
	  this.drawLine()
      // 暂存角色id
      this.roleId = curRole.id
    },

    // 给角色分配权限
    async assignRights () {
    },
	
	//  echarts 
	drawLine(){
		// var echarts = require('echarts');
        // 基于准备好的dom，初始化echarts实例
		this.$nextTick(()=>{
			let myChart = this.$echarts.init(document.getElementById('myChart'));
					// let myChart =this.$echarts.init(this.$refs.myChart)
			//         let dom = this.$refs.mychart;
			// 		this.myChart = echarts.init(dom);
					// 绘制图表
			        myChart.setOption({
			            title: { text: '在Vue中使用echarts' },
			            tooltip: {},
			            xAxis: {
			                data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
			            },
			            yAxis: {},
			            series: [{
			                name: '销量',
			                type: 'bar',
			                data: [5, 20, 36, 10, 10, 20]
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

