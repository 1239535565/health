<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="病历号" prop="number">
        <el-input
          v-model="queryParams.number"
          placeholder="请输入病历号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="registrationList" @selection-change="handleSelectionChange">
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="病历号" align="center" prop="number" width="100"/>
      <el-table-column label="挂号金额" align="center" prop="amountPayable" width="100"/>
      <el-table-column label="结算类型" align="center" prop="type" width="100"/>
      <el-table-column label="挂号级别" align="center" prop="level" width="100"/>
      <!-- <el-table-column label="患者姓名" align="center" prop="patientName" />
      <el-table-column label="性别" align="center" prop="ssex" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.ssex==0" type="success">男</el-tag>
          <el-tag v-if="scope.row.ssex==1" type="danger">女</el-tag>
          <el-tag v-if="scope.row.ssex==2" type="danger">未知</el-tag>
        </template>
      </el-table-column> -->
      <el-table-column label="年龄" align="center" prop="age" />
      <!-- <el-table-column label="生日" align="center" prop="birthday" >
          <template slot-scope="scope">
              <span>{{ parseTime(scope.row.birthday, '{y}-{m}-{d}') }}</span>
            </template>
      </el-table-column> -->
      <el-table-column label="预约就诊时间" align="center" prop="time" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.time, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="就诊医生" align="center" prop="doctorName" />
       <el-table-column label="状态" align="center" prop="status" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status==1" type="success">已处理</el-tag>
          <el-tag v-if="scope.row.status==0" type="danger">未处理</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="是否采纳" align="center" prop="accept" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.accept===1" type="success">已采纳</el-tag>
          <el-tag v-if="scope.row.accept===2" type="danger">未采纳</el-tag>
          <el-tag v-if="scope.row.accept===undefined" type="info">未处理</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="满意度" align="center" prop="evaluate" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.evaluate==='' || scope.row.evaluate === undefined" type="info">未评价</el-tag>
          <el-tag v-else-if="scope.row.evaluate!==''" type="success">{{ scope.row.evaluate }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column
            label="操作"
            align="center"
            width="250"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button v-if="scope.row.accept !== undefined && scope.row.evaluate === undefined" 
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="handleEvaluate(scope.row)"
              >评价</el-button>
              <el-button v-if="scope.row.accept === undefined && scope.row.status === 1"
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="handleAccept(scope.row)"
              >采纳</el-button>
              <el-button v-if="scope.row.accept === undefined && scope.row.status === 1"
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="handleNotAccept(scope.row)"
              >不采纳</el-button>
              <el-button v-if="scope.row.isInvoice === false"
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="handleInvoice(scope.row)"
              >开发票</el-button>
              <el-button v-if="scope.row.isInvoice === true"
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="handleInvoiceAgain(scope.row)"
              >重打发票</el-button>
              <el-button v-if="scope.row.isInvoice === true"
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="handleInvoiceAgain(scope.row)"
              >补打发票</el-button>
            </template>

          </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { listRegistration, accept, notAccept, evaluate, openInvoice } from "@/api/registration"
import { Message } from "element-ui";

export default {
  name: "Registration",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 挂号表格数据
      registrationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        number: undefined,
        patientName: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        recipe: [
          { required: true, message: "请输入处方信息", trigger: "blur" }
        ],
        remake: [
          { required: true, message: "请输入病例详情", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询挂号列表 */
    getList() {
      this.loading = true;
      listRegistration(this.queryParams).then(response => {
        this.registrationList = response.data.data.records;
        this.total = response.data.data.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        number: undefined,
        time: undefined,
        createTime: undefined,
        doctorId: undefined,
        patientsId: undefined,
        patientName: undefined,
        doctorName: undefined,
        remake: undefined,
        recipe: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handleAccept(row) {
      accept(row.id).then(res => {
        if (res.data.status === 200) {
          this.getList()
          Message({
            message: "采纳成功",
            type: "success",
            duration: 5 * 1000
          });
        }
      })
    },
    handleNotAccept(row) {
      notAccept(row.id).then(res => {
        if (res.data.status === 200) {
          this.getList()
          Message({
            message: "已设置不采纳",
            type: "success",
            duration: 5 * 1000
          });
        }
      })
    },
    handleEvaluate(row) {
      this.$prompt('请输入评价:', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      })
        .then(({ value }) => {
          evaluate(row.id, value).then(res => {
            if (res.data.status === 200) {
              this.getList()
              Message({
                message: "评价成功",
                type: "success",
                duration: 5 * 1000
              });
            } else {
              Message({
                message: res.data.msg,
                type: "error",
                duration: 5 * 1000
              });
            }
          })
        })
    },
    handleInvoice(row) {
      const id = row.id
      openInvoice(id).then(res => {
        if (res.data.status === 200) {
          const routeOne = this.$router.resolve({
            path: "/invoice/index/" + id
          });
          window.open(routeOne.href, '_blank')
        }
        this.getList()
      })
    },
    handleInvoiceAgain(row) {
      const id = row.id
      const routeOne = this.$router.resolve({
        path: "/invoice/index/" + id
      });
      window.open(routeOne.href, '_blank')
    }
  }
};
</script>