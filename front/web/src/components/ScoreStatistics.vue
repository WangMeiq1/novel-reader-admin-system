<template>
  <div style="padding: 20px;">
    <h2>成绩统计分析</h2>

    <!-- 课程选择器 -->
    <el-row :gutter="20" style="margin-bottom: 20px;">
      <el-col :span="6">
        <el-select v-model="selectedCourseId" placeholder="请选择课程" @change="loadStatistics">
          <el-option
            v-for="item in courseList"
            :key="item.courseId"
            :label="item.courseName"
            :value="item.courseId"
          />
        </el-select>
      </el-col>
      <el-col :span="4">
        <el-button type="primary" @click="loadStatistics">查询统计</el-button>
      </el-col>
    </el-row>

    <!-- 统计概览卡片 -->
    <el-row :gutter="20" style="margin-bottom: 20px;">
      <el-col :span="6">
        <el-card shadow="hover">
          <div style="text-align: center;">
            <div style="font-size: 14px; color: #909399;">选课人数</div>
            <div style="font-size: 32px; font-weight: bold; color: #409EFF;">{{ statistics.count }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div style="text-align: center;">
            <div style="font-size: 14px; color: #909399;">平均分</div>
            <div style="font-size: 32px; font-weight: bold; color: #67C23A;">{{ statistics.avg }} 分</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div style="text-align: center;">
            <div style="font-size: 14px; color: #909399;">最高分</div>
            <div style="font-size: 32px; font-weight: bold; color: #E6A23C;">{{ statistics.max }} 分</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div style="text-align: center;">
            <div style="font-size: 14px; color: #909399;">及格率</div>
            <div style="font-size: 32px; font-weight: bold; color: #F56C6C;">{{ statistics.passRate }}%</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 成绩分布图表（使用Element Plus的进度条模拟） -->
    <el-card shadow="hover">
      <template #header>
        <span>成绩分布</span>
      </template>
      <div>
        <div style="margin-bottom: 10px; display: flex; align-items: center;">
          <span style="width: 60px; font-size: 14px;">90-100分</span>
          <el-progress
            :percentage="distribution.excellent"
            :color="'#67C23A'"
            style="flex: 1;"
          />
          <span style="width: 40px; font-size: 14px;">{{ distribution.excellentCount }}人</span>
        </div>
        <div style="margin-bottom: 10px; display: flex; align-items: center;">
          <span style="width: 60px; font-size: 14px;">60-89分</span>
          <el-progress
            :percentage="distribution.pass"
            :color="'#409EFF'"
            style="flex: 1;"
          />
          <span style="width: 40px; font-size: 14px;">{{ distribution.passCount }}人</span>
        </div>
        <div style="margin-bottom: 10px; display: flex; align-items: center;">
          <span style="width: 60px; font-size: 14px;">0-59分</span>
          <el-progress
            :percentage="distribution.fail"
            :color="'#F56C6C'"
            style="flex: 1;"
          />
          <span style="width: 40px; font-size: 14px;">{{ distribution.failCount }}人</span>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getStatistics } from '../api/score'
import { getCoursePage } from '../api/course'

const selectedCourseId = ref(null)
const courseList = ref([])

const statistics = reactive({
  count: 0,
  avg: 0,
  max: 0,
  min: 0,
  passRate: 0
})

const distribution = reactive({
  excellent: 0,
  excellentCount: 0,
  pass: 0,
  passCount: 0,
  fail: 0,
  failCount: 0
})

// 加载课程列表（用于选择器）
const loadCourses = async () => {
  try {
    const res = await getCoursePage(1, 100, '')
    courseList.value = res.data.records || []
    if (courseList.value.length > 0) {
      selectedCourseId.value = courseList.value[0].courseId
      loadStatistics()
    }
  } catch (error) {
    ElMessage.error('加载课程列表失败')
  }
}

// 加载统计数据（实际开发中建议后端直接返回分布数据，此处为模拟）
const loadStatistics = async () => {
  if (!selectedCourseId.value) {
    ElMessage.warning('请选择课程')
    return
  }
  try {
    const res = await getStatistics(selectedCourseId.value)
    Object.assign(statistics, res.data)

    // 模拟分布数据（实际应由后端返回）
    // 这里用随机数模拟，实际项目中后端应该返回真实分布
    const total = statistics.count || 1
    distribution.excellentCount = Math.round(total * (Math.random() * 0.3 + 0.2))
    distribution.failCount = Math.round(total * (Math.random() * 0.2 + 0.1))
    distribution.passCount = total - distribution.excellentCount - distribution.failCount

    distribution.excellent = Math.round((distribution.excellentCount / total) * 100)
    distribution.pass = Math.round((distribution.passCount / total) * 100)
    distribution.fail = Math.round((distribution.failCount / total) * 100)
  } catch (error) {
    ElMessage.error('加载统计数据失败')
  }
}

onMounted(() => {
  loadCourses()
})
</script>