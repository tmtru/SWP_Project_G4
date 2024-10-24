/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author khong
 */
public class CloudinaryConfig {
    public Cloudinary getCloudinary() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "djfut5xhj",
                "api_key", "292823514644648",
                "api_secret", "HDEOW6Lx2WBMBBGCbMufXERIEHQ"));
        return cloudinary;
    }

    public static void main(String[] args) {
        File file = new File("E:/Downloads/example.jpg");
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "djfut5xhj",
                "api_key", "292823514644648",
                "api_secret", "HDEOW6Lx2WBMBBGCbMufXERIEHQ"));
        cloudinary.config.secure = true;
        try {
            Map up = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            System.out.println((String)up.get("secure_url"));
        }catch(Exception e){
            
            System.out.println(e.getMessage());
        }

    }
}
