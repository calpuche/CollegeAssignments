//
//  SecondViewController.swift
//  Party Reminders
//
//  Created by Carlos Alpuche on 5/1/17.
//  Copyright © 2017 Carlos Alpuche. All rights reserved.
//

import UIKit

class SecondViewController: UIViewController,UIImagePickerControllerDelegate,UINavigationControllerDelegate {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.

    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "usePhoto" {
            if let viewController = segue.destination as? editInfoViewController {
                if(imageView != nil){
                    viewController.theImagePassed = imageView as UIImageView
                }
            }
        }
    }
    @IBAction func savePhoto(_ sender: Any) {
        
    }
   
    @IBAction func takePhoto(_ sender: Any) {
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
        imageView.image = image
        self.dismiss(animated: true, completion: nil)
    }
    @IBOutlet weak var imageView: UIImageView!
    
}

