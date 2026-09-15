<template>
  <div class="content">
    <h1 class="title">
      <slot name="title"></slot>
      <hr/>
    </h1>
    <ul>
      <li>
        <div class="food-item">
          <span class="item-index"></span>
          <span class="item-title">美食名称</span>
          <span class="item-name">窗口</span>
          <span class="item-price">价格</span>
        </div>
      </li>

      <li  v-for="(item,index) in foodList" :key="index">
        <div class="food-item" >
          <span class="item-index">
           {{ index + 1 }}
          </span>
          <span class="item-title" @click="goAlbum(item,item.id)">{{ replaceFName(item.name) }}</span>
          <span class="item-name">{{ replaceLName(item.name) }}</span>
          <span class="item-price">{{ item.price }}元</span>
        </div>
      </li>
    </ul>
  </div>
</template>
<script>

import {mixin} from '../mixins'

export default {
  name: 'album-content',
  mixins: [mixin],
  data () {
    return {
      sid: '',       //传来的美食id
    }
  },
  
  created () {
    // console.log('---------接收到的美食合集', this.foodList)
    
    let sid = localStorage.getItem('sid')
    if (sid && sid !== 'undefined') {
      console.log('=sid==>>>>>>', sid)
      this.sid = JSON.parse(sid)
    } else {
      this.sid = this.$route.params.sid
      localStorage.setItem('sid', JSON.stringify(this.$route.params.sid))
    }
  },
  beforeDestroy () {
    localStorage.removeItem('sid')
  },
  props: [
    'foodList'
  ],
  methods: {
    goAlbum(item,id){
      this.$store.commit("setTempList",item); 
			  if(id){           //美食
					this.$router.push({path:`/foodWindow-album/${item.foodWindowId}/${item.id}`});
				}else{                  
					alert("error");
				}
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/album-content.scss';
</style>
