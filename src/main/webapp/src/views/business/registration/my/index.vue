<template>
  <div>
    <h1 style="text-align:center">挂号</h1>
    <el-row :gutter="15" style="margin-left:20px;margin-right:70px;margin-top:80px">
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
        <el-col :span="12">
          <el-form-item label="科室" prop="depId">
            <el-select v-model="formData.depId" placeholder="请选择科室" clearable :style="{width: '100%'}" @change="getDoctorById(formData.depId)">
              <el-option v-for="item in departmentOptions" :key="item.id" :label="item.depName"
                :value="item.id" :disabled="item.disabled"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="医生" prop="doctorId">
            <el-select v-model="formData.doctorId" placeholder="请选择医生" clearable :style="{width: '100%'}">
              <el-option v-for="item in doctorOptions" :key="item.userId" :label="item.nickName"
                :value="item.userId" :disabled="item.disabled"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="是否预约" prop="isForward">
              <el-switch v-model="formData.isForward" active-text="预约" inactive-text="不预约"></el-switch>
            </el-form-item>
        </el-col>
        <el-col :span="12" v-if="formData.isForward === true">
            <el-form-item label="预约时间" prop="time">
              <el-date-picker type="datetime" v-model="formData.time" format="yyyy-MM-dd HH:mm:ss" :default-value="new Date()"
                value-format="yyyy-MM-dd HH:mm:ss" :style="{width: '100%'}" placeholder="请选择预约时间" clearable>
              </el-date-picker>
            </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="是否需要病历" prop="isCases">
              <el-switch v-model="formData.isCases" active-text="需要" inactive-text="不需要"></el-switch>
            </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="挂号级别" prop="level">
            <el-input v-model="formData.level" placeholder="请输入挂号级别" :maxlength="20" clearable
              :style="{width: '100%'}"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item size="large">
            <el-button type="primary" @click="handlePay">支付</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>

    <el-dialog title="扫码支付" :visible.sync="qrcode" width="300px">
      <el-row>
          <el-col :span="8">
             <img src="./qrcode.png" style="text-align:center;"/>
          </el-col>
        </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">已完成支付</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getDepartment, getDoctorById } from "@/api/common"
import { add } from "@/api/registration"
import { Message } from "element-ui";
export default {

  components: {},
  props: [],
  data() {
    return {
      qrcode: false,
      departmentOptions: undefined,
      doctorOptions: undefined,
      formData: {
        doctorId: undefined,
        time: undefined
      },
      rules: {
        depId: [{
          required: true,
          message: '请选择科室',
          trigger: 'change'
        }],
        userId: [{
          required: true,
          message: '请选择医生',
          trigger: 'change'
        }],
        time: [{
          required: true,
          message: '请选择预约时间',
          trigger: 'change'
        }]
      },
      depIdOptions: [{
        "label": "选项一",
        "value": 1
      }, {
        "label": "选项二",
        "value": 2
      }],
      doctorIdOptions: [{
        "label": "选项一",
        "value": 1
      }, {
        "label": "选项二",
        "value": 2
      }]
    }
  },
  computed: {},
  watch: {},
  created() {
    getDepartment().then(response => {
      this.departmentOptions = response.data.data
    })
  },
  mounted() {
  },
  methods: {
    cancel() {
      this.qrcode = false
    },
    handlePay() {
      if (this.formData.depId === undefined) {
        Message({
          message: "请选择科室",
          type: "error",
          duration: 5 * 1000
        });
      } else if (this.formData.doctorId === undefined) {
        Message({
          message: "请选择医生",
          type: "error",
          duration: 5 * 1000
        });
      } else if (this.formData.isForward === true) {
        if (this.formData.time === undefined) {
          Message({
            message: "请选择预约时间",
            type: "error",
            duration: 5 * 1000
          });
        } else if (this.formData.level === undefined) {
          Message({
            message: "请输入挂号级别",
            type: "error",
            duration: 5 * 1000
          });
        } else {
          this.qrcode = true
        }
      } else if (this.formData.level === undefined) {
        Message({
          message: "请输入挂号级别",
          type: "error",
          duration: 5 * 1000
        });
      } else {
        this.qrcode = true
      }
    },
    onOpenDialog() {
      this.updateOpen = true
    },
    getDoctorById(id) {
      this.formData.doctorId = undefined
      getDoctorById(id).then(res => {
        this.doctorOptions = res.data.data
      })
    },
    submitForm(data) {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        // TODO 提交表单
        if (valid) {
          add(this.formData).then(res => {
            if (res.data.status === 200) {
              Message({
                message: "挂号成功，" + res.data.data,
                type: "success",
                duration: 5 * 1000
              });
              this.resetForm()
              this.cancel()
            } else {
              this.msgError(res.msg);
            }
          })
        }
      })
    },
    resetForm() {
      this.$refs['elForm'].resetFields()
    }
  }
}

</script>
<style>
</style>
