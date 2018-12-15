function TSiteData(index,url, date, description, login) {

this.index=index;
this.url=url;
this.date=date;
this.description=description;
this.login=login;
this.isSelected=false;

this.addDinamicRow=addDinamicRowFT;
this.ClickRow=ClickRowFT;

//this.addDinamicRow();
}
//----------------------------------------------------------
function addDinamicRowFT(index,namber_row,numberChild)
{
 // метод выводит в таблицу информацию о сайте
 // вызывается при выполнении сортировки: sortData
 
  var objTbody=document.getElementById("dynamicTableBody");
  var currow0 = document.createElement("TR");
  currow0.setAttribute("id",this.index+"_Row_");
 // currow0.setAttribute("class","tr2");
//   currow0.style.height="30px";
  
  var cell0 = document.createElement("TD");
  cell0.setAttribute("colSpan",5);
  cell0.innerHTML="<table class='table' width='100%'><tr class='tr2' onclick='Data["+this.index+"].ClickRow()' align='center'>"+
                    "<td width='20px'>"+(this.index+1)+"</td>"+
                    "<td width='190px'>"+this.url+"</td>"+
                    "<td width='190px'>"+(this.description+"...")+"</td>"+
                    "<td width='90px'>"+this.date+"</td>"+
                    "<td width='50px'>"+this.login+"</td>"+
                    "</tr></table>";
  currow0.appendChild(cell0);
  objTbody.appendChild(currow0);

}
//----------------------------------------------------------
function ClickRowFT(){
 this.isSelected=!this.isSelected;
  var obj = document.getElementById(this.index+"_Row_");
  if(obj);
    else  return;
  if(this.isSelected){
   obj.style.background="#d34b85";
  }else{
     obj.style.background="#E1E1E1";
  }
  
  setBusinessRules();
}
//----------------------------------------------------------
//----------------------------------------------------------
//----------------------------------------------------------


//----ОБЩИЕ МЕТОДЫ СТРАНИЦЫ---------------------------------
//----------------------------------------------------------
function getSelectedSite(){
  var s=""
  for(var i=0;i<Data.length;i++){
   if(Data[i].isSelected){
    if(s.length>0) s+="^"
    s+= Data[i].url;
   }
  }
  return s;
}
//----------------------------------------------------------
function addSite(){
 document.forms[0].actionId.value="addSite";
 document.forms[0].submit();
}
//----------------------------------------------------------
function viewSite(){
 document.forms[0].pkID.value=getSelectedSite();
 document.forms[0].actionId.value="viewSite";
 document.forms[0].submit();

}
//----------------------------------------------------------
function editSite(){
 document.forms[0].pkID.value=getSelectedSite();
 document.forms[0].actionId.value="editSite";
 document.forms[0].submit();
}
//----------------------------------------------------------
function deleteSite(){
 document.forms[0].action=contextPath+"/siteedit";
 document.forms[0].actionId.value="delete";
 document.forms[0].pkID.value=getSelectedSite();
 var a=document.forms[0].pkID.value.split("^");
 var urls="";
 for(var i=0;i<a.length;i++){
  urls+=a[i]+"\n";
 }
 
 if(confirm(localesStr['shouldYouDel']+" "+(a.length>1?localesStr['sites']:localesStr['site'])+" ?\n"+urls))
   document.forms[0].submit();
}
//----------------------------------------------------------
function exit(){
window.location.replace(contextPath+"/login.jsp");
}
//----------------------------------------------------------
function setBusinessRules(){
  //устанавливаем правла для элементов формы
  
 var count=0;
 for(var i=0;i<Data.length;i++){
   if(Data[i].isSelected){
     count++;
   }
 }
 
 // если выбрано более одного сайта или ничего не выбрано - делаем неактивными кнопки просмотра и редактирования
 var dis=false;
 if(count>1 || count==0) dis=true;
 document.getElementById("bt_view_site").disabled=dis;
 document.getElementById("bt_edit_site").disabled=dis;
 
 if(count==0)
   document.getElementById("bt_del_site").disabled=true;
  else 
   document.getElementById("bt_del_site").disabled=false;
  
}
//----------------------------------------------------------
function sortData(obj){
   clearList();
   if(obj.value=='1')
     Data.sort(compareName);  
   if(obj.value=='2')
     Data.sort(compareDate);  
   for(var i=0;i<Data.length;i++){
    Data[i].index=i;
   	Data[i].addDinamicRow();
   }
   
   document.forms[0].by_sort.value=obj.value;
 }
//----------------------------------------------------------
function clearList(){
 for(var i=0;i<Data.length;i++){
  var id_row= Data[i].index+"_Row_";
  var elm = document.getElementById(id_row);
  if(elm)
    elm.parentNode.removeChild(elm);
 }
 
 setBusinessRules();
}
//----------------------------------------------------------
function compareName(a,b){
if(a.url==b.url) return 0;
if(a.url<b.url) return -1;
if(a.url>b.url) return 1;
}
//----------------------------------------------------------
function compareDate(a,b){
if(a.date==b.date) return 0;
if(a.date<b.date) return -1;
if(a.date>b.date) return 1;
}
//----------------------------------------------------------
function prevBuff(){
var url= contextPath+"/list?actionId=nextList&siteUrl="+prev_site+"&what=prev";
window.dataIframe.location.replace(url);
}
//----------------------------------------------------------
function nextBuff(){
var url= contextPath+"/list?actionId=nextList&siteUrl="+last_site+"&what=next";
window.dataIframe.location.replace(url);
}
//----------------------------------------------------------
function backToList(){
var s="";
if(document.forms[0].pkID)
s="&pkID="+escape(document.forms[0].pkID.value);
//s+="&last_site="+document.forms[0].last_site.value;
s+="&prev_site="+document.forms[0].prev_site.value;
s+="&by_sort="+document.forms[0].by_sort.value;
//alert(s)
window.location.replace(contextPath+"/list?actionId=initList"+s);
}
//----------------------------------------------------------
function checkUrl(evt) {
// при вводе URL-а не даем вводить кавычки
	evt = (evt) ? evt : (window.event) ? window.event : ""
	if (evt) {
		var thingPressed = ""
		var elem = (evt.target) ? evt.target : evt.srcElement
		if (evt.which) {
			thingPressed = evt.which
		} else 
			if (elem.type == "text") {
				thingPressed = evt.keyCode
			}
	}

  if(parseInt(thingPressed)==34 || parseInt(thingPressed)==39)
    return false;

 	return true;
}
//-----------------------------------------------------------------------------
