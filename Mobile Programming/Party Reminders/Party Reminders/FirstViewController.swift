//
//  FirstViewController.swift
//  Party Reminders
//
//  Created by Carlos Alpuche on 5/1/17.
//  Copyright © 2017 Carlos Alpuche. All rights reserved.
//

import UIKit

class FirstViewController: UIViewController,UITableViewDataSource,UITableViewDelegate {
    var photos = [Photos]()
    
    @IBOutlet weak var tblParties: UITableView!

    @IBAction func btbLogOUt(_ sender: Any) {
         UserDefaults.standard.set(false, forKey: "loggedIn")
    }
    
    override func viewDidLoad() {
        tblParties.reloadData()
        super.viewDidLoad()
        
        
        // Do any additional setup after loading the view, typically from a nib.
    }
    override func viewDidAppear(_ animated: Bool)
    {
        // Do any additional setup after loading the view, typically from a nib.
        do
        {
            photos = try context.fetch(Photos.fetchRequest())
        }
        catch{}
        tblParties.reloadData()
        super.viewDidAppear(false)
}
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return photos.count
    }
    //
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        //we name prototype cell
        let cellIdentifier = "cell"
        // for basic types let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath)
        //the "as !TableViewCell" we are downcasting; meaning we are changing a type of class to a subclass
        let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath) as! TableViewCell
    
        //cell.textLabel?.text = restaurants[indexPath.row]
        let photo = photos[indexPath.row]
        //print(photo.place!)
        //print("entered making a cell")
        cell.lblPlace.text = photo.place!
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "MM-dd-yyyy"
        let date = photo.date
        let dateString = dateFormatter.string(from:date as! Date)
        cell.lblDate.text = dateString
        return cell
        
    }
    var valueToPass:Int!
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        print("You selected cell #\(indexPath.row)!")
        
        // Get Cell Label
        let indexPath = tableView.indexPathForSelectedRow!
        valueToPass = indexPath.row
        print(valueToPass)
        performSegue(withIdentifier: "showInfo", sender: self)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "showInfo" {
            if let viewController = segue.destination as? ShowInfoViewController {
               viewController.passedValue = valueToPass
            }
        }
    }
    func printPhotos()
    {
        do
        {
            photos = try context.fetch(Photos.fetchRequest())
            
            for each in photos
            {
                print ("people: \(each.people!) \n place: \(each.place!)\n Date: \(each.date)")
            }
        }
        catch{}
    }

    

}

