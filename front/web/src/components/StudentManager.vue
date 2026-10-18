<template>
  <div style="padding: 20px;">
    <h2>学生信息管理</h2>

    <!-- 搜索栏 -->
    <el-row :gutter="20" style="margin-bottom: 20px;">
      <el-col :span="6">
        <el-input
          v-model="keyword"
          placeholder="输入学号或姓名搜索"
          clearable
          @clear="loadData"
        />
      </el-col>
      <el-col :span="4">
        <el-button type="primary" @click="loadData">搜索</el-button>
        <el-button type="success" @click="openAddDialog">添加学生</el-button>
      </el-col>
    </el-row>

    <!-- 数据表格 -->
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="studentId" label="学号" width="150" />
      <el-table-column prop="name" label="姓名" width="120" />
      <el-table-column prop="gender" label="性别" width="80" align="center">
        <template #default="{ row }">
          <el-tag :type="row.gender === '男' ? 'primary' : 'success'">
            {{ row.gender }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="className" label="班级" width="200" />
      <el-table-column prop="phone" label="联系电话" width="150" />
      <el-table-column prop="enrollDate" label="入学日期" width="150" />
      <el-table-column label="操作" width="200" align="center">
        <template #default="{ row }">
          <el-button size="small" type="primary" @click="openEditDialog(row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(row.studentId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      v-model:current-page="pageNum"
      v-model:page-size="pageSize"
      :page-sizes="[5, 10, 20]"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="loadData"
      @current-change="loadData"
      style="margin-top: 20px; justify-content: flex-end;"
    />

    <!-- 添加/编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form ref="formRef" :model="formData" :rules="rules" label-width="100px">
        <el-form-item label="学号" prop="studentId">
          <el-input v-model="formData.studentId" :disabled="isEdit" placeholder="请输入学号" />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="formData.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="formData.gender">
            <el-radio label="男">男</el-radio>
            <el-radio label="女">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="班级" prop="className">
          <el-input v-model="formData.className" placeholder="请输入班级" />
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="formData.phone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="入学日期" prop="enrollDate">
          <el-date-picker
            v-model="formData.enrollDate"
            type="date"
            placeholder="选择日期"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确认</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getStudentPage, addStudent, updateStudent, deleteStudent } from '../api/student'

const tableData = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(5)
const keyword = ref('')
const dialogVisible = ref(false)
const dialogTitle = ref('添加学生')
const isEdit = ref(false)
const formRef = ref(null)

const formData = reactive({
  studentId: '',
  name: '',
  gender: '男',
  className: '',
  phone: '',
  enrollDate: ''
})

const rules = {
  studentId: [{ required: true, message: '请输入学号', trigger: 'blur' }],
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }]
}

const loadData = async () => {
  try {
    const res = await getStudentPage(pageNum.value, pageSize.value, keyword.value)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
    console.log('加载数据成功:', tableData.value)
  } catch (error) {
    console.error('加载数据失败:', error)
    ElMessage.error('加载数据失败')
  }
}

const openAddDialog = () => {
  isEdit.value = false
  dialogTitle.value = '添加学生'
  formData.studentId = ''
  formData.name = ''
  formData.gender = '男'
  formData.className = ''
  formData.phone = ''
  formData.enrollDate = ''
  dialogVisible.value = true
}

const openEditDialog = (row) => {
  isEdit.value = true
  dialogTitle.value = '编辑学生'
  Object.assign(formData, row)
  dialogVisible.value = true
}

const submitForm = async () => {
  try {
    await formRef.value.validate()
    if (isEdit.value) {
      await updateStudent(formData)
      ElMessage.success('更新成功')
    } else {
      await addStudent(formData)
      ElMessage.success('添加成功')
    }
    dialogVisible.value = false
    loadData()
  } catch (error) {
    console.error('提交失败:', error)
    ElMessage.error('操作失败')
  }
}

const handleDelete = (studentId) => {
  ElMessageBox.confirm('确认删除该学生吗？', '提示', { type: 'warning' })
    .then(async () => {
      await deleteStudent(studentId)
      ElMessage.success('删除成功')
      loadData()
    })
    .catch(() => {})
}

onMounted(() => {
  loadData()
})
</script>