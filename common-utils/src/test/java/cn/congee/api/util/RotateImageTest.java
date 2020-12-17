package cn.congee.api.util;

import org.springframework.mock.web.MockMultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

/**
 * @desc:
 * @author: wgb
 * @date: 2020-12-17 13:00
 */
public class RotateImageTest {

    public static void main(String[] args) {
        try {
            //file->multipartfile
            File file = new File("秋秋不开心了_20201017_143245.jpg");
            FileInputStream inputStream = new FileInputStream(file);
            MockMultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);

            BufferedImage src = ImageIO.read(multipartFile.getInputStream());
            //顺时针旋转90度
            BufferedImage desc1 = RotateImageUtil.Rotate(src, 90);
            ImageIO.write(desc1, "jpg", new File("/home/swifthealth/图片/90.jpg"));
            //顺时针旋转180度
            BufferedImage desc2 = RotateImageUtil.Rotate(src, 180);
            ImageIO.write(desc2, "jpg", new File("/home/swifthealth/图片/180.jpg"));
            //顺时针旋转270度
            BufferedImage desc3 = RotateImageUtil.Rotate(src, 270);
            ImageIO.write(desc3, "jpg", new File("/home/swifthealth/图片/270.jpg"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
