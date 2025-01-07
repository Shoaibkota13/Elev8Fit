package com.fitness.elev8fit.utils

import android.content.Context
import android.net.Uri
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID

class S3Uploader(private val context: Context) {
    private val accessKey = s3acess.YOUR_ACCESS_KEY
    private val secretKey = s3acess.YOUR_SECRET_KEY
    private val bucketName = s3acess.YOUR_BUCKET_NAME
    
    private val s3Client = AmazonS3Client(BasicAWSCredentials(accessKey, secretKey))

    suspend fun uploadImage(imageUri: Uri): String = withContext(Dispatchers.IO) {
        try {
            // Create a temporary file from Uri
            val inputStream = context.contentResolver.openInputStream(imageUri)
            val file = File.createTempFile("upload_", ".jpg", context.cacheDir)
            file.outputStream().use { outputStream ->
                inputStream?.copyTo(outputStream)
            }

            // Generate unique file name
            val fileName = "chat_images/${UUID.randomUUID()}.jpg"
            
            // Upload file
            s3Client.putObject(bucketName, fileName, file)
            
            // Delete temporary file
            file.delete()
            
            // Return the public URL
            "https://$bucketName.s3.amazonaws.com/$fileName"
            
        } catch (e: Exception) {
            throw e
        }
    }
} 