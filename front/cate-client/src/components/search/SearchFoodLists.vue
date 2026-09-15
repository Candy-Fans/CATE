<template>
    <div class="search-food-Lists">
       <content-list :contentList="albumDatas"></content-list>
    </div>
</template>
<script>
import ContentList from '../ContentList';
import {getFoodListOfLikeTitle} from '../../api/index';
import {mixin} from "../../mixins";

export default {
    name: 'search-food-lists',
    components:{
        ContentList
    },
    data(){
        return{
            albumDatas: []
        }
    },
    mounted(){
        this.getSearchList();
    },
    methods:{
        getSearchList(){
            if(!this.$route.query.keywords){
                this.notify('您输入的内容为空','warning')
            }else{
                getFoodListOfLikeTitle(this.$route.query.keywords)
                    .then(res =>{
                        if(res){
                            this.albumDatas = res
                        }else{
                            this.notify('暂无该美食相关信息','warning')
                        }
                    })
            }
        }
    }
}
</script>

<style lang="scss" scoped>
@import '../../assets/css/search-food-lists.scss';
</style>