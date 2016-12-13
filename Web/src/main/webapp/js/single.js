function getPointerPos(){
    return 5;
}
function getScore(id){
    return 71;
}
function realScore(){
    var score0 = getScore(id);
    var radialObj4 = $('#score').radialIndicator({
        barColor: {
            0: '#FF0000',
            33: '#FFFF00',
            66: '#0066FF',
            100: '#33CC33'
        },
        radius : 100
    }).data('radialIndicator');
    radialObj4.animate(score0);
}
function changeAddPic(){
    var temp = document.getElementById("addButton");
    
    if (add==0) {
        temp.style.backgroundImage = "url("+'images/button-add-1.png'+")";
        add = 1;
    }
    else{
        temp.style.backgroundImage = "url("+'images/button-add.png'+")";
        add = 0;
    }
}
function barPointer(){
    var pos = getPointerPos();
    var temp = document.getElementById("bar");
    temp.style.backgroundImage = "url("+'images/bar_'+pos+'.png'+")";
}