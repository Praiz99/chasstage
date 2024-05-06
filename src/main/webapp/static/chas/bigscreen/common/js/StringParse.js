function nameParse(name) {
    // var len = name.length;
    // var firstName = name.split('')[0];
    // var s="某"
    // if(len>2){
    //     s="某某"
    // }
    return name;
}

function wait(ms){
    var start = new Date().getTime();
    var end = start;
    while(end < start + ms) {
        end = new Date().getTime();
    }
}