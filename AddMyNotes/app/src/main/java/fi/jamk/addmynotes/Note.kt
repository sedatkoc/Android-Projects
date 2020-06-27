package fi.jamk.addmynotes

class Note {
    var id:Int=0
    var title:String=""
    var details:String=""
    constructor()
    constructor(title:String,details:String){
        this.title=title
        this.details=details

    }
    constructor(id:Int,title:String,details:String){
        this.id=id
        this.title=title
        this.details=details
    }
}