<template>
  <div class="rights">
    <!-- 查询和添加 -->
    <div class="sys-research">
      <el-row>
        <el-col :span="8">
          <!-- 查询 -->
          <el-input
            placeholder="请输入搜索内容"
            v-model="searchText"
            class="input-with-select">
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="searchRight"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button plain @click="RightDialogVisible = true">添加角色</el-button>
        </el-col>
      </el-row>
    </div>
    <!-- start 添加权限弹窗 -->
    <el-dialog
      title="权限添加"
      :visible.sync="RightDialogVisible"
      @close="closeRightAddDialog"
      width="30%">
      <el-form label-width="80px" :model="rightForm" ref="rightAddFormRef">
        <el-form-item label="权限名称" prop="permName">
          <el-input v-model="rightForm.permName"></el-input>
        </el-form-item>
        <el-form-item label="连接地址" prop="url">
          <el-input v-model="rightForm.url"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="RightDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addRight('rightAddFormRef')">确 定</el-button>
      </span>
    </el-dialog>
    <!-- end 添加权限弹窗 -->
     <!-- start 编辑权限弹窗 -->
    <el-dialog
      title="权限编辑"
      :visible.sync="editRightDialogVisible"
      width="30%">
      <el-form label-width="80px" :model="editRightForm" ref="rigthEditFormRef">
        <el-form-item label="权限名称" prop="permName">
          <el-input v-model="editRightForm.permName"></el-input>
        </el-form-item>
        <el-form-item label="连接地址" prop="url">
          <el-input v-model="editRightForm.url"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelEditRight">取 消</el-button>
        <el-button type="primary" @click="editRight">确 定</el-button>
      </span>
    </el-dialog>
    <!-- end 编辑权限弹窗 -->
    <el-table
      :data="rightsList"
      stripes
      style="width: 100%" border>
      <!-- type="index" 表示索引列， 可以通过 :index 指定一个方法，来自定义索引 -->
     <!--  <el-table-column type="index" :index="indexMethod">
      </el-table-column> -->
      <el-table-column
        prop="permId"
        label="权限ID"
        width="180"
        align="center">
      </el-table-column>
      <el-table-column
        prop="permName"
        label="权限名称"
        width="250"
        align="center">
      </el-table-column>
      <el-table-column
        prop="url"
        label="链接地址"
        width="280"
        align="center">
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button size="mini" plain type="primary" icon="el-icon-edit"
          @click="handleEditRight(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" plain type="danger" icon="el-icon-delete"
          @click="handleDeleteRight(scope.$index, scope.row.permId)">删除</el-button>
         <!--  <el-button size="mini" plain type="success"icon="el-icon-check"@click="showRoleDialog(scope.row)">
            分配权限
          </el-button> -->
        </template>
      </el-table-column>
     <!--  <el-table-column
        prop="level"
        label="层级">
        <template slot-scope="scope">
          <span v-if="scope.row.level === '0'">一级</span>
          <span v-else-if="scope.row.level === '1'">二级</span>
          <span v-else>三级</span>
        </template>
      </el-table-column> -->
    </el-table>
    <div style="margin-top: 10px;text-align: center">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
// 引入
import axios from 'axios'
export default {
  data () {
    return {
      searchText: '', // 搜索文本框
      // dialogTitle:'权限添加',
      RightDialogVisible: false, // 是否显示添加弹窗
      editRightDialogVisible: false, // 是否显示编辑弹窗
      rightForm: {// 权限表单
        permName: '',
        url: ''
      },
      editRightForm: {
        permId: '',
        permName: '',
        url: ''
      },
      total: 0,
      pageSize: 10,
      currentPage: 1,
      rightsList: []
    }
  },

  created () {
    this.searchRight()
  },

  methods: {
    // 查询
    searchRight () {
      console.log('权限查询事件')
      var _this = this
      var permName = _this.searchText
      axios.post('restful/permissions/select',
        {
          'permName': permName === '' ? '' : _this.searchText
        },
        {
          headers: {
            'content-type': 'application/json'
          },
          withCredentials: true
        }).then(function (response) {
        console.log(response)
        _this.rightsList = response.data.permissions
        _this.total = response.data.permissions.length
      })
        .catch(function (error) {
          console.log(error)
        })
    },
    // 添加
    addRight () { // 添加权限接口没调通
      var _this = this
      var param = _this.rightForm
      console.log(param)
      axios.post('restful/permissions/save', param,
        {
          headers: {
            'content-type': 'application/json'
          },
          withCredentials: true
        }).then(function (response) {
        console.log(response)
        if (response.data.returnCode === '1111') {
          _this.RightDialogVisible = false
          _this.searchRight()
          _this.$message({
            type: 'success',
            message: '添加权限成功'
          })
        } else {
          _this.$message({
            type: 'error',
            message: '添加权限失败'
          })
        }
      })
        .catch(function (error) {
          console.log(error)
        })
    },
    // 关闭添加权限对话框
    closeRightAddDialog () {
      // 重置表单
      this.$refs.rightAddFormRef.resetFields()
    },
    // 编辑
    editRight () {
      var _this = this
      var para = _this.editRightForm
      console.log(para)
      axios.post('restful/permissions/update', para,
        {
          headers: {
            'content-type': 'application/json'
          },
          withCredentials: true
        }).then(function (response) {
        console.log(response)
        if (response.data.returnCode === '1111') {
          _this.editRightDialogVisible = false
          _this.searchRight()
          _this.$message({
            type: 'success',
            message: '编辑权限信息成功'
          })
        } else {
          _this.$message({
            type: 'error',
            message: '编辑权限信息失败'
          })
        }
      })
        .catch(function (error) {
          console.log(error)
        })
    },
    handleEditRight (index, row) {
      var _this = this
      console.log('编辑权限')
      _this.editRightDialogVisible = true
      _this.editRightForm = Object.assign({}, row) // 解决修改弹出框数据时，表格数据会发生同步改变
    },
    // 删除
    async handleDeleteRight (index, permId) {
      var _this = this
      try {
        await _this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        axios.post('restful/permissions/delete', {
          'permId': permId
        },
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
          _this.searchRight()
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
    RightDialogClose () {

    },
    cancelRight () {
      this.$refs.rigthFormRef.resetFields() // 移除校验结果并重置字段值
      this.RightDialogVisible = false
      this.rightForm.permName = ''
      this.rightForm.url = ''
    },
    cancelEditRight () {
      this.$refs.rigthEditFormRef.resetFields() // 移除校验结果并重置字段值
      this.editRightDialogVisible = false
    },
    handleSizeChange (val) {
      console.log(`每页 ${val} 条`)
      this.pageSize = val
    },
    handleCurrentChange (val) {
      console.log(`当前页: ${val}`)
      this.currentPage = val
    }
  }
}
</script>

<style lang="less" scoped>
  .sys-research{
    height: 60px;
    padding:20px 20px 0;
  }
</style>
