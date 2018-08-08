//
//  ViewController.swift
//  SelfieRUs
//
//  Created by Carlos Alpuche on 3/22/17.
//  Copyright © 2017 Carlos Alpuche. All rights reserved.
//

import UIKit
import Social

class ViewController: UIViewController,UIImagePickerControllerDelegate,UINavigationControllerDelegate {
    
    //on take pick button
    @IBOutlet weak var btnPicture: UIBarButtonItem!
    
    @IBOutlet weak var imagePhoto: UIImageView!

    @IBAction func takePicture(_ sender: Any) {
        let imagePicker = UIImagePickerController()
        
        imagePicker.delegate = self
        if UIImagePickerController.isSourceTypeAvailable(.camera){
            imagePicker.sourceType = .camera
            //make sure the front camera is available
            if UIImagePickerController.isCameraDeviceAvailable(.front){
                imagePicker.cameraDevice = .front
            }
            else{
                imagePicker.cameraDevice = .rear
            }
        }
        else{
            
            imagePicker.sourceType = .photoLibrary
        }
        
        self.present(imagePicker, animated: true, completion: nil)
        
        
    }
   
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingImage image:UIImage!, editingInfo:[AnyHashable:Any]!) {
        imagePhoto.image = image
        self.dismiss(animated: true, completion: nil)
    }
    
    @IBAction func postPicture(_ sender: Any) {
        let social = SLComposeViewController(forServiceType: SLServiceTypeTwitter)
        social?.add(imagePhoto.image)
        self.present(social!, animated: true, completion: nil)
        
        
        
    }
    
    
}

