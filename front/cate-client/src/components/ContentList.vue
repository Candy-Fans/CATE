<template>
    <div class="content-list">
        <ul class="section-content">
            <li class="content-item" v-for="(item,index) in contentList" :key="index">
                <div class="kuo" @click="goAlbum(item,item.name,item.foodWindowId)">
                    <img class="item-img" :src="attachImageUrl(item.pic)">
                </div> 
                <p class="item-name">{{item.name||item.title}}</p>
                <p class="item-nums" v-if="item.nums!=null">被点击次数：{{item.nums}}</p>
            </li>
        </ul>
    </div>
</template>
<script>
import {mixin} from '../mixins';
export default {
    name: 'content-list',
    mixins: [mixin],
    props: ['contentList'],
    methods: {
        goAlbum(item,type,foodWindowId){
            this.$store.commit("setTempList",item);
            if(type){  
			    if(foodWindowId){           //窗口
					this.$router.push({path:`foodWindow-album/${item.foodWindowId}/${item.id}`});
					
				}else{                  //美食
					this.$router.push({path:`foodWindow-album/${item.id}`});
				}
            }else{                      //美食合集
                this.$router.push({path:`food-list-album/${item.id}`});
            }
        }
    }
}
</script>
<style lang="scss" scoped>
@import '../assets/css/content-list.scss';
</style>
