<template>
    <div class="search-foods">
       <album-content :foodList="listOfFoods"></album-content>
    </div>
</template>
<script>
import {mapGetters} from "vuex";
import {mixin} from "../../mixins";
import AlbumContent from "../AlbumContent";

export default {
    name: 'search-foods',
    components:{
        AlbumContent
    },
    mixins: [mixin],
    computed:{
        ...mapGetters([
            'listOfFoods'
        ])
    },
    mounted() {
        this.getFood();
    }
}
</script>

<style lang="scss" scoped>
@import '../../assets/css/search-foods.scss';
</style>