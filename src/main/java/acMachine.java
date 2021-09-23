import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;



public class acMachine {
    //查找全部的模式串
    public ArrayList<String> findAll(String text){
        root = new TreeNode(null, ' ');
        //可以找到 转移到下个节点 不能找到在失败指针节点中查找直到为root节点
        ArrayList<String> results = new ArrayList<String>();
        int index = 0;
        TreeNode mid = root;
        while(index <text.length()){

            TreeNode temp = null;

            while(temp ==null){
                temp = mid.getSonsNode(text.charAt(index));
                if(mid ==root){
                    break;
                }
                if(temp==null){
                    mid = mid.failure;
                }
            }
            //mid为root 再次进入循环 不需要处理  或者 temp不为空找到节点 节点位移
            if(temp!=null) mid = temp;

            for (String result : mid.getResults()) {
                results.add(result);
            }
            index++;
        }
        return results;
    }




    class TreeNode {
        private TreeNode parent;
        private TreeNode failure;
        private char ch;
        private ArrayList<String> results;
        private HashMap<Character, TreeNode> sonHash;
        private TreeNode[] sonsNode;

        //初始化TreeNode
        public TreeNode(TreeNode parent, char ch) {
            this.parent = parent;
            this.ch = ch;
            results = new ArrayList<String>();
            sonHash = new HashMap<Character, TreeNode>();
            sonsNode = new TreeNode[]{};
        }



        //增加子节点
        public void addSonNode(TreeNode node) {
            sonHash.put(node.ch, node);
            Iterator<TreeNode> iterator = sonHash.values().iterator();
            for (int i = 0; i < sonsNode.length; i++) {
                if (iterator.hasNext()) {
                    sonsNode[i] = iterator.next();
                }
            }
        }

        public boolean containNode(char ch) {
            return getSonsNode(ch) != null;
        }

        public TreeNode getParent() {
            return parent;
        }

        public void setParent(TreeNode parent) {
            this.parent = parent;
        }

        public TreeNode getFailure() {
            return failure;
        }

        public void setFailure(TreeNode failure) {
            this.failure = failure;
        }

        public char getCh() {
            return ch;
        }

        public void setCh(char ch) {
            this.ch = ch;
        }

        public ArrayList<String> getResults() {
            return results;
        }

        public void addResults(String results) {
            this.results.add(results);
        }

        public HashMap<Character, TreeNode> getSonHash() {
            return sonHash;
        }

        public void setSonHash(HashMap<Character, TreeNode> sonHash) {
            this.sonHash = sonHash;
        }

        public TreeNode getSonsNode(char ch) {
            return sonHash.get(ch);
        }

        public TreeNode[] getSonsNode() {
            return sonsNode;
        }

        public void setSonsNode(TreeNode[] sonsNode) {
            this.sonsNode = sonsNode;
        }
    }

    //建立ac自动机，建树，增加失败指针
    public void acMachineBuilder(ArrayList<String> keywords) {
        //建树
        buildTree(keywords);
        //增加失败指针
        addFailure();
    }

    private TreeNode root;


    private void buildTree(ArrayList<String> keywords) {
        root = new TreeNode(null, ' ');
        for (String eachKeyWords : keywords) {
            TreeNode temproot = root;
            for (char ch : eachKeyWords.toCharArray()) {
                if (temproot.containNode(ch)) {
                    //如果子节点包含ch
                    temproot = temproot.getSonsNode(ch);
                } else {
                    //如果不包含
                    TreeNode newsonNode = new TreeNode(temproot, ch);
                    temproot.addSonNode(newsonNode);
                    temproot = newsonNode;
                }
            }
        }
    }

    private void addFailure() {
        //设置二层失败指针为根节点 收集三层节点

        //DFA遍历所有节点 设置失败节点 原则: 节点的失败指针在父节点的失败指针的子节点中查找 最大后缀匹配
        ArrayList<TreeNode> mid = new ArrayList<TreeNode>();//过程容器
        for (TreeNode node : root.getSonsNode()) {
            node.failure = root;
            for (TreeNode treeNode : node.getSonsNode()) {
                mid.add(treeNode);
            }
        }

        //广度遍历所有节点设置失败指针 1.存在失败指针 2.不存在到root结束
        while (mid.size() > 0) {
            ArrayList<TreeNode> temp = new ArrayList<TreeNode>();//子节点收集器

            for (TreeNode node : mid) {

                TreeNode r = node.getParent().failure;

                while (r != null && !r.containNode(node.getCh())) {
                    r = r.failure;//没有找到,保证最大后缀 (最后一个节点字符相同)
                }

                //是根结
                if (r == null) {
                    node.failure = root;
                } else {
                    node.failure = r.getSonsNode(node.getCh());
                    //重叠后缀的包含
                    for (String result : node.failure.getResults()) {
                        node.addResults(result);
                    }
                }

                //收集子节点
                for (TreeNode treeNode : node.getSonsNode()) {
                    temp.add(treeNode);
                }

            }
            mid = temp;
        }
        root.failure = root;
    }

}
