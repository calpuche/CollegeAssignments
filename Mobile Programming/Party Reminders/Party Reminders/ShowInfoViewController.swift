//
//  ShowInfoViewController.swift
//  Party Reminders
//
//  Created by Carlos Alpuche on 5/3/17.
//  Copyright © 2017 Carlos Alpuche. All rights reserved.
//

import UIKit

class ShowInfoViewController: UIViewController {
    var passedValue:Int = 0
    override func viewDidLoad() {
        
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
    var photos = [Photos]()
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    override func viewDidAppear(_ animated: Bool)
    {
        // Do any additional setup after loading the view, typically from a nib.
        print(passedValue)
        do
        {
            photos = try context.fetch(Photos.fetchRequest())
        }
        catch{}
        lblPeople.text=photos[passedValue].people
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "MM-dd-yyyy"
        let date = photos[passedValue].date
        let dateString = dateFormatter.string(from:date as! Date)
        lblDate.text=dateString
        lblPlace.text=photos[passedValue].place
        let image : UIImage = UIImage(data: photos[passedValue].photo as! Data)!
        imageView.image=image
        super.viewDidAppear(false)
    }

    
    @IBOutlet weak var lblPlace: UILabel!
    @IBOutlet weak var lblDate: UILabel!
    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet weak var lblPeople: UILabel!
    

    
}
