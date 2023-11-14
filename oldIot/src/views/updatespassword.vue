<template>

  <div>
      <el-form label-width="80px" :model="customerForm" :rules="rules"  ref="addcustomerFormRef">
        <el-form-item label="新密码" prop="spassword1">
          <el-input v-model="customerForm.spassword1"></el-input>
        </el-form-item>
        <el-form-item label="再次输入" prop="spassword2">
          <el-input v-model="customerForm.spassword2"></el-input>
        </el-form-item>
      </el-form>

      <el-button type="primary" @click="addRoles('addcustomerFormRef')">确 定</el-button>
  </div>


</template>

<script>
// 引入
import axios from 'axios'

export default {
  data () {
		var checkPhone = (rule, value, callback) => {
		  const phoneReg = /^1[3|4|5|7|8][0-9]{9}$/
		  if (!value) {
		    return callback(new Error('电话号码不能为空'))
		  }
		  setTimeout(() => {
		    // Number.isInteger是es6验证数字是否为整数的方法,但是我实际用的时候输入的数字总是识别成字符串
		    // 所以我就在前面加了一个+实现隐式转换

		    if (!Number.isInteger(+value)) {
		      callback(new Error('请输入数字值'))
		    } else {
		      if (phoneReg.test(value)) {
		        callback()
		      } else {
		        callback(new Error('电话号码格式不正确'))
		      }
		    }
		  }, 100)
		}
       var checkAge = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('年龄不能为空'));
        }
        setTimeout(() => {
          if (!Number.isInteger(+value)) {
            callback(new Error('请输入数字值'));
          } else {
            if (+value < 0) {
              callback(new Error('必须大于零'));
            } else if(+value > 150){
							callback(new Error('必须小于150'));
						} else{
              callback();
            }
          }
        }, 1000);
      }
    return {
      searchText: '', // 角色查询内容
      customerDialogVisible: false,
      oldEditDialogVisible: false,
      customerForm: {
        sid:'',
        saccount:'',
        spassword:'',
        spassword1:'',
        spassword2:'',
        sname:'',
        semail:'',
        stime:'',
        customerName: '',
        customerAge: '',
				customerSex: '',
				customerPhone: '',
				customerAddress: ''
      },
      oldEditForm: {
        pid:'',
        paccount:'',
        ppassword:'',
        pname:'',
        pemail:'',
        ptime:'',
        customerName: '',
        customerAge: '',
        customerSex: '',
        customerPhone: '',
        customerAddress: '',
				customerId: ''
      },
      pid:'',
      paccount:'',
      ppassword:'',
      pname:'',
      pemail:'',
      ptime:'',
      popoverRole: true,
      total: 0,
      pageSize: 10,
      currentPage: 1,
      customerList: [{
		  oldId: '1',
		  customerName: '小黄',
		  customerAge: '35',
		  customerSex: '男',
		  customerPhone: '15415263698',
		  customerAddress: '南昌'
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
      roleId: -1,
			rules: {
						customerPhone: [
						  { validator: checkPhone, trigger: 'blur'}
						],
						customerAge: [
            { validator: checkAge, trigger: 'blur' }
          ]
					  }
    }
  },
  created () {
    this.searchCustomers()
  },
  methods: {
    // 查询
    searchCustomers () {
				var _this = this
				var customerName = _this.searchText

       axios.post('restful/produce/selectAll',
         {
           // 'customerName': customerName === '' ? '' : _this.searchText
         },
         {
           headers: {
             'content-type': 'application/json'
           },
           withCredentials: true
         }).then(function (response) {
         _this.customerList = response.data.produces
         _this.total = response.data.produces.length
     console.log(_this.customerList)
       })
         .catch(function (error) {
           console.log(error)
         })

    },
    // 添加角色
    addRoles () {
      var _this = this
      if (_this.customerForm.spassword1 == _this.customerForm.spassword2 && this.customerForm.spassword1 !="" && _this.customerForm.spassword1 !=null) {
        _this.customerForm.spassword =_this.customerForm.spassword1
        _this.customerForm.saccount=localStorage.getItem('saccount')
        axios.post('restful/sale/updatePassword',
          _this.customerForm,
          {
            headers: {
              'content-type': 'application/json'
            },
            withCredentials: true
          }).then(function (response) {
          console.log(response)
          if (response.data.returnCode === '1111') {
            _this.customerDialogVisible = false
            _this.searchCustomers()
            _this.$message({
              type: 'success',
              message: '修改成功'
            })
          } else {
            _this.$message({
              type: 'error',
              message: '修改失败'
            })
          }
        })
          .catch(function (error) {
            console.log(error)
          })
      }else {
        _this.$message({
          type: 'error',
          message: '请重新填写密码'
        })
      }
    },
    // 关闭添加角色对话框
    closeRoleAddDialog () {
      // 重置表单
      this.$refs.addcustomerFormRef.resetFields()
    },
    // 编辑
    editRole () {
      var _this = this
      axios.post('restful/produce/update', _this.oldEditForm,
        {
          headers: {
            'content-type': 'application/json'
          },
          withCredentials: true
        }).then(function (response) {
        console.log(response)
        if (response.data.returnCode === '1111') {
          _this.oldEditDialogVisible = false
          _this.$message({
            type: 'success',
            message: '编辑信息成功'
          })
          _this.searchCustomers()
        } else {
          _this.$message({
            type: 'error',
            message: '编辑信息失败'
          })
        }
      })
        .catch(function (error) {
          console.log(error)
        })
    },
    // 删除
    async deleteRole (paccount) {
      console.log(paccount)

      var _this = this
      try {
        await _this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        // console.log(customerId)
        axios.post('restful/produce/deleteByAccount', {'paccount': paccount},
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
          _this.searchCustomers()
        })
          .catch(function (error) {
            console.log(error)
          })
      } catch (err) {
        console.log(err)
        // 取消删除
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      }
    },
    // 编辑信息
    handleEditRole (index, row) {
      this.oldEditDialogVisible = true
      this.oldEditForm = Object.assign({}, row) // 解决修改弹出框数据时，表格数据会发生同步改变
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
      // 暂存角色id
      this.roleId = curRole.id
    },

    // 给角色分配权限
    async assignRights () {
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
