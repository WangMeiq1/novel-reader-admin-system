<template>
  <div style="padding: 20px;">
    <h2>课程信息管理</h2>

    <!-- 搜索栏 -->
    <el-row :gutter="20" style="margin-bottom: 20px;">
      <el-col :span="6">
        <el-input
          v-model="keyword"
          placeholder="输入课程名称搜索"
          clearable
          @clear="loadData"
        />
      </el-col>
      <el-col :span="4">
        <el-button type="primary" @click="loadData">搜索</el-button>
        <el-button type="success" @click="openAddDialog">添加课程</el-button>
      </el-col>
    </el-row>

    <!-- 数据表格 -->
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="courseId" label="课程编号" width="120" align="center" />
      <el-table-column prop="courseName" label="课程名称" min-width="180" />
      <el-table-column prop="credit" label="学分" width="100" align="center">
        <template #default="scope">
          <el-tag type="primary">{{ scope.row.credit }} 分</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="teacher" label="授课教师" width="150" align="center" />
      <el-table-column label="操作" width="200" align="center" fixed="right">
        <template #default="scope">
          <el-button size="small" type="primary" @click="openEditDialog(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row.courseId)">删除</el-button>
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
        <el-form-item label="课程名称" prop="courseName">
          <el-input v-model="formData.courseName" placeholder="请输入课程名称" />
        </el-form-item>
        <el-form-item label="学分" prop="credit">
          <el-input-number v-model="formData.credit" :min="1" :max="10" />
        </el-form-item>
        <el-form-item label="授课教师" prop="teacher">
          <el-input v-model="formData.teacher" placeholder="请输入授课教师姓名" />
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
import { getCoursePage, addCourse, updateCourse, deleteCourse } from '../api/course'

const tableData = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(5)
const keyword = ref('')
const dialogVisible = ref(false)
const dialogTitle = ref('添加课程')
const isEdit = ref(false)
const formRef = ref(null)

const formData = reactive({
  courseId: null,
  courseName: '',
  credit: 2,
  teacher: ''
})

const rules = {
  courseName: [{ required: true, message: '请输入课程名称', trigger: 'blur' }],
  teacher: [{ required: true, message: '请输入授课教师', trigger: 'blur' }]
}

const loadData = async () => {
  try {
    const res = await getCoursePage(pageNum.value, pageSize.value, keyword.value)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } catch (error) {
    ElMessage.error('加载数据失败')
  }
}

const openAddDialog = () => {
  isEdit.value = false
  dialogTitle.value = '添加课程'
  formData.courseId = null
  formData.courseName = ''
  formData.credit = 2
  formData.teacher = ''
  dialogVisible.value = true
}

const openEditDialog = (row) => {
  isEdit.value = true
  dialogTitle.value = '编辑课程'
  Object.assign(formData, row)
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  try {
    if (isEdit.value) {
      await updateCourse(formData)
      ElMessage.success('更新成功')
    } else {
      await addCourse(formData)
      ElMessage.success('添加成功')
    }
    dialogVisible.value = false
    loadData()
  } catch (error) {
    ElMessage.error('操作失败，请检查输入')
  }
}

const handleDelete = (courseId) => {
  ElMessageBox.confirm('确认删除该课程吗？删除后相关成绩也将被删除！', '提示', {
    type: 'warning'
  }).then(async () => {
    await deleteCourse(courseId)
    ElMessage.success('删除成功')
    loadData()
  }).catch(() => {})
}

onMounted(() => {
  loadData()
})
</script>