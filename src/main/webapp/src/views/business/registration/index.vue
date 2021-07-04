<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="病例号" prop="number">
        <el-input
          v-model="queryParams.number"
          placeholder="请输入病例号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="患者姓名" prop="patientName">
        <el-input
          v-model="queryParams.patientName"
          placeholder="请输入患者姓名"
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
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="病例号" align="center" prop="number" />
      <el-table-column label="患者姓名" align="center" prop="patientName" />
      <el-table-column label="性别" align="center" prop="ssex" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.ssex==0" type="success">男</el-tag>
          <el-tag v-if="scope.row.ssex==1" type="danger">女</el-tag>
          <el-tag v-if="scope.row.ssex==2" type="danger">未知</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="年龄" align="center" prop="age" />
      <el-table-column label="支付金额" align="center" prop="amountPayable" />
      <el-table-column label="结算类型" align="center" prop="type" />
      <el-table-column label="挂号级别" align="center" prop="level" />
      <el-table-column label="预约就诊时间" align="center" prop="time" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.time, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="就诊医生" align="center" prop="doctorName" />
      
       <el-table-column label="状态" align="center" prop="status" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status==1" type="success">已处理</el-tag>
          <el-tag v-if="scope.row.status==0" type="danger">未处理</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.status !== 1"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >开处方</el-button>
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

    <!-- 开处方对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="唯一编号" prop="number">
          <el-input v-model="form.number" placeholder="请输入唯一编号" disabled/>
        </el-form-item>
        <el-form-item label="诊断医生" prop="doctorName">
          <el-input v-model="form.doctorName" placeholder="请输入挂号医生" disabled/>
        </el-form-item>
        <el-form-item label="患者" prop="patientName">
          <el-input v-model="form.patientName" placeholder="请输入挂号患者姓名" disabled/>
        </el-form-item>
        <el-form-item label="病例详情" prop="remake">
        <el-input v-model="form.remake" type="textarea" placeholder="请输入病例详情"
          :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"></el-input>
      </el-form-item>
      <el-form-item label="处方信息" prop="recipe">
        <el-input v-model="form.recipe" type="textarea" placeholder="请输入处方信息"
          :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"></el-input>
      </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRegistration, updateRegistration } from "@/api/registration"
import { makeCase } from "@/api/case"
import { Message, MessageBox } from "element-ui"

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

    /** 开处方按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      this.form = row
      this.open = true
      this.title = "开处方"
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id !== undefined) {
            makeCase(this.form).then(response => {
              if (response.data.status === 200) {
                Message({
                  message: "成功",
                  type: "success",
                  duration: 5 * 1000
                });
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            })
          }
        }
      });
    }
  }
};
</script>