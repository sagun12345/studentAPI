package com.Repository

import com.API.MyAPiRequest
import com.API.ServiceBuilder
import com.API.StudentAPI
import com.kiran.student.entity.Student
import com.kiran.student.response.AddStudentResponse
import com.kiran.student.response.DeleteStudentResponse
import com.kiran.student.response.GetAllStudentResponse
import com.kiran.student.response.ImageResponse
import okhttp3.MultipartBody

class StudentRepository :
        MyAPiRequest() {
    private val studentAPI=
        ServiceBuilder.buildService(StudentAPI::class.java)

    //Add student
    suspend fun addStudent(student: Student):AddStudentResponse{
        return apiRequest {
            studentAPI.addStudent(
                ServiceBuilder.token!!, student
            )
        }
    }
    suspend fun getAllStudents(): GetAllStudentResponse {
        return apiRequest {
            studentAPI.getallStudents(ServiceBuilder.token!!)

        }
    }
    suspend fun deleteStudents(id:String):DeleteStudentResponse{
        return apiRequest {
            studentAPI.deleteStudent(ServiceBuilder.token!!,id)
        }

    }
    suspend fun uploadImage(id: String, body: MultipartBody.Part)
            : ImageResponse {
        return apiRequest {
            studentAPI.uploadImage(ServiceBuilder.token!!, id, body)
        }
    }
    }

