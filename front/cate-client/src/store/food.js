const food = {
    state:{
        listOfFoods: [],     //当前美食列表
        url: '',                //美食地址
        id: '',                  //美食id
        title: '',                  //美食名
        artist: '',                 //窗口名
        picUrl: '',                 //美食图片
        tempList: {},               //单个美食合集信息或窗口信息
    },
    getters: {
        listOfFoods: state => {
            let listOfFoods = state.listOfFoods;
            if(!listOfFoods.length){
                listOfFoods = JSON.parse(window.sessionStorage.getItem('listOfFoods')||null); 
            }
            return listOfFoods;
        },
        url: state => {
            let url = state.url;
            if(!url){
                url = JSON.parse(window.sessionStorage.getItem('url')||null); 
            }
            return url;
        },
        id: state => {
            let id = state.id;
            if(!id){
                id = JSON.parse(window.sessionStorage.getItem('id')||null); 
            }
            return id;
        },
        title: state => {
            let title = state.title;
            if(!title){
                title = JSON.parse(window.sessionStorage.getItem('title')||null); 
            }
            return title;
        },
        artist: state => {
            let artist = state.artist;
            if(!artist){
                artist = JSON.parse(window.sessionStorage.getItem('artist')||null); 
            }
            return artist;
        },
        picUrl: state => {
            let picUrl = state.picUrl;
            if(!picUrl){
                picUrl = JSON.parse(window.sessionStorage.getItem('picUrl')||null); 
            }
            return picUrl;
        },
        tempList: state => {
            let tempList = state.tempList;
            if(!tempList){
                tempList = JSON.parse(window.sessionStorage.getItem('tempList')||null); 
            }
            return tempList;
        },
        listIndex: state => {
            let listIndex = state.listIndex;
            if(!listIndex){
                listIndex = JSON.parse(window.sessionStorage.getItem('listIndex')||null); 
            }
            return listIndex;
        },
    },
    mutations: {
        setListOfFoods: (state,listOfFoods) => {
            state.listOfFoods = listOfFoods;
            window.sessionStorage.setItem('listOfFoods',JSON.stringify(listOfFoods));
        },
        setUrl: (state,url) => {
            state.url = url;
            window.sessionStorage.setItem('url',JSON.stringify(url));
        },
        setId: (state,id) => {
            state.id = id;
            window.sessionStorage.setItem('id',JSON.stringify(id));
        },
        setTitle: (state,title) => {
            state.title = title;
            window.sessionStorage.setItem('title',JSON.stringify(title));
        },
        setArtist: (state,artist) => {
            state.artist = artist;
            window.sessionStorage.setItem('artist',JSON.stringify(artist));
        },
        setPicUrl: (state,picUrl) => {
            state.picUrl = picUrl;
            window.sessionStorage.setItem('picUrl',JSON.stringify(picUrl));
        },
        setTempList: (state,tempList) => {
            state.tempList = tempList;
            window.sessionStorage.setItem('tempList',JSON.stringify(tempList));
        },
        setListIndex: (state,listIndex) => {
            state.listIndex = listIndex;
            window.sessionStorage.setItem('listIndex',JSON.stringify(listIndex));
        },
    }
}

export default food