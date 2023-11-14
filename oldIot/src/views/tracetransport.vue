<template>
  <div class="roles">
    <!-- 查询和添加 -->
<!--    <div class="sys-research">-->
<!--      <el-row class="row-search">-->
<!--        <el-col :span="8">-->
<!--          &lt;!&ndash; 查询 &ndash;&gt;-->
<!--&lt;!&ndash;          <el-input&ndash;&gt;-->
<!--&lt;!&ndash;            placeholder="请输入搜索内容"&ndash;&gt;-->
<!--&lt;!&ndash;            v-model="searchText"&ndash;&gt;-->
<!--&lt;!&ndash;            class="input-with-select">&ndash;&gt;-->
<!--&lt;!&ndash;            <el-button&ndash;&gt;-->
<!--&lt;!&ndash;              slot="append"&ndash;&gt;-->
<!--&lt;!&ndash;              icon="el-icon-search"&ndash;&gt;-->
<!--&lt;!&ndash;              @click="searchfamily"></el-button>&ndash;&gt;-->
<!--&lt;!&ndash;          </el-input>&ndash;&gt;-->
<!--        </el-col>-->
<!--        <el-col :span="4">-->
<!--          <el-button plain @click="customerDialogVisible = true">创建订单</el-button>-->
<!--        </el-col>-->
<!--      </el-row>-->
<!--    </div>-->


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

<!--    <el-dialog-->
<!--      title="订单填写"-->
<!--      :visible.sync="customerDialogVisible"-->
<!--      @close="closeRoleAddDialog"-->
<!--      width="30%">-->
<!--      <el-form label-width="80px" :model="familyForm" :rules="rules" class="demo-ruleForm"  ref="addfamilyFormRef">-->
<!--&lt;!&ndash;        <el-form-item label="订单id" prop="traid">&ndash;&gt;-->
<!--&lt;!&ndash;          <el-input v-model="familyForm.oldid"></el-input>&ndash;&gt;-->
<!--&lt;!&ndash;        </el-form-item>&ndash;&gt;-->
<!--&lt;!&ndash;        <el-form-item label="生产商" prop="pname">&ndash;&gt;-->
<!--&lt;!&ndash;          <el-input v-model="familyForm.oldid"></el-input>&ndash;&gt;-->
<!--&lt;!&ndash;        </el-form-item>&ndash;&gt;-->
<!--        <el-form-item label="生产地址" prop="paddress">-->
<!--          <el-input v-model="familyForm.paddress"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="负责人" prop="pperson">-->
<!--          <el-input v-model="familyForm.pperson" value=""></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="生产时间" prop="ptime">-->
<!--          <el-input v-model="familyForm.ptime"></el-input>-->
<!--        </el-form-item>-->
<!--&lt;!&ndash;        <el-form-item label="费用(元)" prop="money">&ndash;&gt;-->
<!--&lt;!&ndash;          <el-input v-model="familyForm.money"></el-input>&ndash;&gt;-->
<!--&lt;!&ndash;        </el-form-item>&ndash;&gt;-->
<!--&lt;!&ndash;        <el-form-item label="支付状况" prop="orderstatus">&ndash;&gt;-->
<!--&lt;!&ndash;          <el-input v-model="familyForm.orderstatus"></el-input>&ndash;&gt;-->
<!--&lt;!&ndash;        </el-form-item>&ndash;&gt;-->

<!--      </el-form>-->
<!--      <span slot="footer" class="dialog-footer">-->
<!--      <el-button @click="customerDialogVisible = false">取 消</el-button>-->
<!--      <el-button type="primary" @click="addFamily('addfamilyFormRef')">确 定</el-button>-->
<!--    </span>-->
<!--    </el-dialog>-->
    <!-- end 添加角色弹窗 -->
    <!-- start 编辑角色弹窗 -->
    <el-dialog
      title="事件编辑"
      :visible.sync="oldEditDialogVisible"
      width="30%">
      <el-form label-width="80px" :rules="rules" :model="familyEditForm">
<!--        <el-form-item label="老人id" prop="pname">-->
<!--          <el-input v-model="familyEditForm.pname"></el-input>-->
<!--        </el-form-item>-->
        <el-form-item label="负责人" prop="tperson">
          <el-input v-model="familyEditForm.tperson"></el-input>
        </el-form-item>
        <!--        <el-form-item label="事件" prop="thing" style="display: none;">-->
        <el-form-item label="出发地" prop="tbeginaddress" >
          <el-input v-model="familyEditForm.tbeginaddress"></el-input>
        </el-form-item>
        <el-form-item label="目的地" prop="tarriveaddress">
          <el-input v-model="familyEditForm.tarriveaddress"></el-input>
        </el-form-item>
<!--        <el-form-item label="费用(元)" prop="money">-->
<!--          <el-input v-model="familyEditForm.money"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="支付状况" prop="orderstatus">-->
<!--          <el-input v-model="familyEditForm.orderstatus"></el-input>-->
<!--        </el-form-item>-->
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="oldEditDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="editFamily">确 定</el-button>
    </span>
    </el-dialog>
<!--    <el-table :data="oldsList"-->
<!--              style="width: 100%" border>-->
      <!-- 可展开行 -->
      <el-table :data="oldsList.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                style="width: 85%" border>

<!--      <el-table-column type="index" width="100" align="center"></el-table-column>-->
      <el-table-column prop="traid" label="ID" width="50" align="center"></el-table-column>
      <el-table-column prop="pname" label="生产商" width="100" align="center"></el-table-column>
      <el-table-column prop="paddress" label="生产地址" width="100" align="center"></el-table-column>
      <el-table-column prop="pperson" label="生产负责人" width="100" align="center"></el-table-column>
      <el-table-column prop="ptime" label="生产时间" width="100" align="center"></el-table-column>
        <el-table-column prop="tname" label="运输商" width="100" align="center"></el-table-column>
        <el-table-column prop="tbeginaddress" label="出发地" width="100" align="center"></el-table-column>
        <el-table-column prop="tarriveaddress" label="目的地" width="100" align="center"></el-table-column>
        <el-table-column prop="tperson" label="运输负责人" width="100" align="center"></el-table-column>
<!--        <el-table-column prop="ptime" label="生产时间" width="140" align="center"></el-table-column>-->
<!--      <el-table-column prop="orderstatus" label="状况" width="140" align="center"></el-table-column>-->
        <el-table-column prop="traid" label="操作" align="center"  >

          <template slot-scope="scope" >
            <el-button size="mini" plain type="primary" icon="el-icon-edit"
                       @click="handleeditFamily(scope.$index, scope.row)">编辑
            </el-button>
          </template>
        </el-table-column>
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
      customerDialogVisible: false,
      oldEditDialogVisible: false,
      roleForm: {
        roleName: '',
        roleDesc: ''
      },
      roleEditForm: {
        roleId: '',
        roleName: '',
        roleDesc: ''
      },
      familyForm: {
        traid:'',
        pname:'',
        paddress:'',
        ptime:'',
        pperson:'',
        tperson:'',
        tbeginaddress:'',
        tarriveaddress:'',
        tname:'',
        sperson:'',
        stime:'',
        saddress:'',
        sname:'',
        orderid:'',
        oldid:'',
        name: '',
        thing: '',
        time: '',
        money: '',
        orderstatus:'',
        // familyAddress: '',
        // oldPhone: ''
      },
      familyEditForm: {
        traid:'',
        pname:'',
        paddress:'',
        ptime:'',
        pperson:'',
        tperson:'',
        tbeginaddress:'',
        tarriveaddress:'',
        tname:'',
        sperson:'',
        stime:'',
        saddress:'',
        sname:''
      },
      traid:'',
      pname:'',
      paddress:'',
      ptime:'',
      pperson:'',
      tperson:'',
      tbeginaddress:'',
      tarriveaddress:'',
      tname:'',
      sperson:'',
      stime:'',
      saddress:'',
      sname:'',
      value6: '',
      popoverRole: true,
      total: 0,
      pageSize: 10,
      currentPage: 1,
      oldsList: [{

        traid:'',
        pname:'',
        paddress:'',
        ptime:'',
        pperson:'',
        tperson:'',
        tbeginaddress:'',
        tarriveaddress:'',
        tname:'',
        sperson:'',
        stime:'',
        saddress:'',
        sname:'',
        orderid:'',
        oldid:'',
        thing: '没有',
        time: '',
        money: '',
        orderstatus:'',
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
    console.log('--tra-')
    console.log(localStorage.getItem('user_name'));
    console.log('--tra-')
    // this.getCount()
    this.searchOlds()
    this.familyForm.pname=localStorage.getItem('user_name')
    console.log(this.familyForm.pname)
    this.oldId=localStorage.getItem('oldIdfamily')
    console.log(localStorage.getItem('oldIdfamily'))

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
      console.log('查询')
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
      axios.post('restful/trace/findAll',
        {
          // "oldId":oldId
        },
        {
          headers: {
            'content-type': 'application/json'
          },
          withCredentials: true
        }).then(function (response) {
        console.log(response)
        console.log('====================================')
        // _this.oldsList = response.data.oldPeople
        // _this.total = response.data.oldPeople.length
        _this.oldsList = response.data.findAll
        _this.total = response.data.order.length
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
    //查询账单
    searchfamily () {
      var _this = this
      var familyName = _this.searchText
      axios.post('restful/cost/select',
        {
          'familyName': familyName === '' ? '' : _this.searchText
        },
        {
          headers: {
            'content-type': 'application/json'
          },
          withCredentials: true
        }).then(function (response) {
        _this.familyList = response.data.costs
        _this.total = response.data.costs.length
        console.log(_this.familyList)
      })
        .catch(function (error) {
          console.log(error)
        })

    },
    // 添加账单
    addFamily () {
      var _this = this
      _this.familyForm.pname=localStorage.getItem('user_name')
      axios.post('restful/trace/transportAdd',
        _this.familyForm,
        {
          headers: {
            'content-type': 'application/json'
          },
          withCredentials: true
        }).then(function (response) {
        console.log(response)
        if (response.data.returnCode === '1111') {
          _this.customerDialogVisible = false
          _this.searchfamily()
          _this.$message({
            type: 'success',
            message: '添加成功'
          })
        } else {
          _this.$message({
            type: 'error',
            message: '添加失败'
          })
        }
      })
        .catch(function (error) {
          console.log(error)
        })
    },
    // 编辑账单
    editFamily () {
      var _this = this
      _this.familyEditForm.tname=localStorage.getItem('user_transport')
      axios.post('restful/trace/transportUpdate', _this.familyEditForm,

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
              message: '编辑成功'
          })
          _this.searchfamily()
        } else {
          _this.$message({
            type: 'error',
            message: '编辑失败'
          })
        }
      })
        .catch(function (error) {
          console.log(error)
        })
    },
    // 编辑信息
    handleeditFamily (index, row) {
      this.oldEditDialogVisible = true
      this.familyEditForm = Object.assign({}, row) // 解决修改弹出框数据时，表格数据会发生同步改变
      // console.log(row)
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
            message: '添加成功'
          })
        } else {
          _this.$message({
            type: 'error',
            message: '添加失败'
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
