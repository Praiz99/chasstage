function DijkStra(Udscjdhkw1,RAGstPksA2){this["\x76\x65\x74\x65\x78\x4e\x75\x6d"]=Udscjdhkw1;this["\x65\x64\x67\x65\x41\x72\x72\x61\x79"]=RAGstPksA2;this["\x4d\x41\x58"]=window["\x4e\x75\x6d\x62\x65\x72"]["\x4d\x41\x58\x5f\x56\x41\x4c\x55\x45"];this["\x77\x65\x69\x67\x68\x74"]=[];this["\x64\x69\x73\x74\x65\x6e\x63\x65"]=this["\x4d\x41\x58"];this["\x70\x61\x74\x68"]=[];this["\x70\x61\x74\x68\x44\x69\x73\x74\x65\x6e\x63\x65"]=[];for(var GuY3=0;GuY3<Udscjdhkw1;GuY3++){this["\x77\x65\x69\x67\x68\x74"][GuY3]=[];for(var SPfWG4=0;SPfWG4<Udscjdhkw1;SPfWG4++){this["\x77\x65\x69\x67\x68\x74"][GuY3][SPfWG4]=this["\x4d\x41\x58"]}}if(RAGstPksA2){for(var slr5=0;slr5<RAGstPksA2["\x6c\x65\x6e\x67\x74\x68"];slr5++){this["\x61\x64\x64\x45\x64\x67\x65"](RAGstPksA2[slr5][0],RAGstPksA2[slr5][1],RAGstPksA2[slr5][2])}}}DijkStra["\x70\x72\x6f\x74\x6f\x74\x79\x70\x65"]["\x61\x64\x64\x45\x64\x67\x65"]=function(TKG6,d7,RTMAisS$V8){if(!RTMAisS$V8){console["\x65\x72\x72\x6f\x72"](TKG6+'\x2d\x2d'+d7)}this["\x77\x65\x69\x67\x68\x74"][TKG6][d7]=RTMAisS$V8;this["\x77\x65\x69\x67\x68\x74"][d7][TKG6]=RTMAisS$V8};DijkStra["\x70\x72\x6f\x74\x6f\x74\x79\x70\x65"]["\x64\x69\x6a\x6b"]=function(sjecKM9,XRWMXZ10){var $SpQ11=[];for(var M12=0;M12<this["\x76\x65\x74\x65\x78\x4e\x75\x6d"];M12++){$SpQ11[M12]=[];for(var qkrpUnUA13=0;qkrpUnUA13<this["\x76\x65\x74\x65\x78\x4e\x75\x6d"];qkrpUnUA13++){$SpQ11[M12][qkrpUnUA13]=this["\x77\x65\x69\x67\x68\x74"][M12][qkrpUnUA13]}}var sWoBtucu14=$SpQ11["\x6c\x65\x6e\x67\x74\x68"];var Tgdmo15=[];for(var M12=0;M12<sWoBtucu14;M12++){Tgdmo15[M12]=0}var akZ17=new window["\x41\x72\x72\x61\x79"](sWoBtucu14);for(var M12=0;M12<sWoBtucu14;M12++){akZ17[M12]=sjecKM9+"\x2d"+M12}var YQeOiFs19=new window["\x41\x72\x72\x61\x79"](sWoBtucu14);for(var M12=0;M12<sWoBtucu14;M12++){YQeOiFs19[M12]=0}Tgdmo15[sjecKM9]=0;YQeOiFs19[sjecKM9]=1;for(var XQ21=1;XQ21<=sWoBtucu14-1;XQ21++){var BWcmqGHIp22=-1;var XnnTgzDa23=this["\x4d\x41\x58"];for(var oFWMVY24=0;oFWMVY24<sWoBtucu14;oFWMVY24++){if(YQeOiFs19[oFWMVY24]==0&&$SpQ11[sjecKM9][oFWMVY24]<XnnTgzDa23){XnnTgzDa23=$SpQ11[sjecKM9][oFWMVY24];BWcmqGHIp22=oFWMVY24}}if(BWcmqGHIp22==-1)continue;Tgdmo15[BWcmqGHIp22]=XnnTgzDa23;YQeOiFs19[BWcmqGHIp22]=1;for(var oFWMVY24=0;oFWMVY24<sWoBtucu14;oFWMVY24++){if(YQeOiFs19[oFWMVY24]==0&&$SpQ11[sjecKM9][BWcmqGHIp22]+$SpQ11[BWcmqGHIp22][oFWMVY24]<$SpQ11[sjecKM9][oFWMVY24]){$SpQ11[sjecKM9][oFWMVY24]=$SpQ11[sjecKM9][BWcmqGHIp22]+$SpQ11[BWcmqGHIp22][oFWMVY24];akZ17[oFWMVY24]=akZ17[BWcmqGHIp22]+"\x2d"+oFWMVY24}}}this["\x64\x69\x73\x74\x65\x6e\x63\x65"]=Tgdmo15[XRWMXZ10];var KI26=[];if(this["\x64\x69\x73\x74\x65\x6e\x63\x65"]==0||this["\x64\x69\x73\x74\x65\x6e\x63\x65"]==this["\x4d\x41\x58"])KI26=[];else KI26=akZ17[XRWMXZ10]["\x73\x70\x6c\x69\x74"]("\x2d");for(var M12=0;M12<KI26["\x6c\x65\x6e\x67\x74\x68"];M12++){if(M12==0)this["\x70\x61\x74\x68\x44\x69\x73\x74\x65\x6e\x63\x65"][0]=1;else this["\x70\x61\x74\x68\x44\x69\x73\x74\x65\x6e\x63\x65"][M12]=$SpQ11[KI26[M12-1]][KI26[M12]]}this["\x70\x61\x74\x68"]=KI26;return KI26};DijkStra["\x70\x72\x6f\x74\x6f\x74\x79\x70\x65"]["\x67\x65\x74\x45\x64\x67\x65\x56\x61\x6c\x75\x65\x73"]=function(){return this["\x70\x61\x74\x68\x44\x69\x73\x74\x65\x6e\x63\x65"]};DijkStra["\x70\x72\x6f\x74\x6f\x74\x79\x70\x65"]["\x67\x65\x74\x50\x61\x74\x68\x56\x65\x72\x74\x65\x78\x65\x73"]=function(){return this["\x70\x61\x74\x68"]};DijkStra["\x70\x72\x6f\x74\x6f\x74\x79\x70\x65"]["\x67\x65\x74\x53\x68\x6f\x72\x74\x44\x69\x73\x74\x65\x6e\x63\x65"]=function(){return this["\x64\x69\x73\x74\x65\x6e\x63\x65"]};