-- 1. 创建数据库
DROP DATABASE IF EXISTS score_management;
CREATE DATABASE score_management DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE score_management;

-- 2. 创建学生表
DROP TABLE IF EXISTS student;
CREATE TABLE student (
    student_id VARCHAR(20) PRIMARY KEY COMMENT '学号',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    gender ENUM('男', '女') DEFAULT '男' COMMENT '性别',
    class_name VARCHAR(50) COMMENT '班级',
    phone VARCHAR(15) COMMENT '联系电话',
    enroll_date DATE COMMENT '入学日期'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生信息表';

-- 3. 创建课程表
DROP TABLE IF EXISTS course;
CREATE TABLE course (
    course_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '课程编号',
    course_name VARCHAR(100) NOT NULL COMMENT '课程名称',
    credit INT DEFAULT 2 COMMENT '学分',
    teacher VARCHAR(50) COMMENT '授课教师'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程信息表';

-- 4. 创建成绩表
DROP TABLE IF EXISTS score;
CREATE TABLE score (
    score_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '成绩ID',
    student_id VARCHAR(20) NOT NULL COMMENT '学号',
    course_id INT NOT NULL COMMENT '课程编号',
    score INT DEFAULT 0 COMMENT '分数',
    FOREIGN KEY (student_id) REFERENCES student(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES course(course_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成绩表';

-- 5. 插入测试数据
-- 插入学生数据
INSERT INTO student (student_id, name, gender, class_name, phone, enroll_date) VALUES
('2024001', '张明', '男', '计算机科学与技术1班', '13800001001', '2024-09-01'),
('2024002', '李红', '女', '计算机科学与技术1班', '13800001002', '2024-09-01'),
('2024003', '王强', '男', '计算机科学与技术2班', '13800001003', '2024-09-01'),
('2024004', '赵丽', '女', '软件工程1班', '13800001004', '2024-09-01'),
('2024005', '刘洋', '男', '软件工程1班', '13800001005', '2024-09-01');

-- 插入课程数据
INSERT INTO course (course_name, credit, teacher) VALUES
('JavaEE开发技术', 3, '王教授'),
('数据结构', 4, '李教授'),
('数据库原理', 3, '张教授'),
('操作系统', 3, '赵教授'),
('软件工程', 2, '孙教授');

-- 插入成绩数据
INSERT INTO score (student_id, course_id, score) VALUES
('2024001', 1, 85), ('2024001', 2, 78), ('2024001', 3, 92),
('2024002', 1, 90), ('2024002', 2, 88), ('2024002', 4, 76),
('2024003', 1, 65), ('2024003', 3, 70), ('2024003', 5, 82),
('2024004', 2, 95), ('2024004', 3, 88), ('2024004', 4, 91),
('2024005', 1, 72), ('2024005', 4, 68), ('2024005', 5, 79);