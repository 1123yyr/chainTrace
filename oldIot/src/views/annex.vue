<template>
  <div class="annex">
    <el-table
      :data="rightsList"
      stripes
      style="width: 100%" border>
      <el-table-column type="index" width="80" label="#"> </el-table-column>

      <el-table-column
        prop="annexName"
        label="文件名"
        width="180">
      </el-table-column>
      <el-table-column
        prop="annexSize"
        label="大小">
      </el-table-column>
      <el-table-column
        prop="annexType"
        label="文件类型">
      </el-table-column>
      <el-table-column
        prop="annexTime"
        label="上传时间">
      </el-table-column>
      <el-table-column label="操作">
      <template slot-scope="scope">
        <!-- 下载 -->
        <el-button
          type="success"
          plain
          size="mini"
          icon="el-icon-download"
          @click="uploadAnnex(scope.row)"
        ></el-button>
        <!-- 编辑 -->
        <el-button
          type="primary"
          plain
          size="mini"
          icon="el-icon-edit"
          @click="showUserEditDialog(scope.row)"
        ></el-button>
        <!-- 删除 -->
        <el-button
          type="danger"
          plain
          size="mini"
          icon="el-icon-delete"
          @click="delUser(scope.row.id)"
        ></el-button>
      </template>
    </el-table-column>
    </el-table>

  <!-- 编辑用户对话框 -->
  <el-dialog title="编辑用户" :visible.sync="isShowUserEditDialog">
    <el-form :model="userEditForm" label-width="80px">
      <el-form-item label="文件名">
        <el-input v-model="userEditForm.annexName"></el-input>
      </el-form-item>
    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button @click="isShowUserEditDialog = false">取 消</el-button>
      <el-button type="primary" @click="editUser">确 定</el-button>
    </div>
  </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      rightsList: [{
        annexName: '机械设备',
        annexSize: '170MB',
        annexType: 'text',
        annexTime: '2019-1-2 09:00'
      }],
      // 编辑用户数据：
      // 控制编辑用户对话框的展示和隐藏
      isShowUserEditDialog: false,
      // 用户编辑表单数据
      userEditForm: {
        annexName: '',
        annexSize: '',
        annexExname: '',
        annexTime: '',
        id: -1
      }
    }
  },

  created () {
    this.getRightsList()
  },

  methods: {
    // 展示编辑用户对话框
    showUserEditDialog (curUser) {
      // console.log(curUser)
      // 展示用户编辑对话框
      this.isShowUserEditDialog = true

      // 给用户编辑表单提供数据
      for (let key in this.userEditForm) {
        this.userEditForm[key] = curUser[key]
      }
    },
    // 展示删除用户对话框
    async delUser (id) {
      try {
        await this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })

        // 确认删除
        // 发送请求，删除用户
        // console.log(this.$http === axios)
        const res = await this.$http.delete(`/users/${id}`, {
          headers: {
            Authorization: localStorage.getItem('token')
          }
        })

        // 刷新列表数据
        this.getUsersList(1, this.searchText)

        this.$message({
          type: 'success',
          message: res.data.meta.msg
        })
      } catch (err) {
        // 取消删除
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      }
    },
    /**
     * 获取权限列表
     */
    async getRightsList () {
      const res = await this.$http.get('/rights/list')

      this.rightsList = res.data.data
    }
  }
}
</script>

<style>
</style>
