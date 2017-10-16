package com.sam.pom.parser;

import java.io.FileReader;
import java.io.Reader;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

public class pomParser 
{
    public static void main( String[] args) throws Exception
    {
    	Reader reader = new FileReader("C:\\Samiran\\workspace_sts\\spring-boot-axon-sample\\pom.xml");
    	try {
    	    MavenXpp3Reader xpp3Reader = new MavenXpp3Reader();
    	    Model baseModel = xpp3Reader.read(reader);
    	    //System.out.println(baseModel.getDependencies());
    	    //baseModel.getParent().getRelativePath();
    	    for(Dependency d:baseModel.getDependencies()){
    	    	System.out.println(d.getGroupId()+":"+d.getArtifactId()+":"+d.getVersion()+":"+d.getVersion()+":"+d.getScope());
    	    }
    	    
    	  /* // final File repositoryDir = new File("C:\\Samiran\\workspace_sts\\spring-boot-axon-sample\\pom.xml");
    	    final Model model = MavenPomReader.readModel("C:\\Samiran\\workspace_sts\\spring-boot-axon-sample\\pom.xml");
    	    
    	 // Print the dependencies on the console
    	 final List<Dependency> dependencies = model.getDependencies();
    	 for (int i = 0; i < dependencies.size(); i++) {
    	     final Dependency dependency = dependencies.get(i);
    	     //System.out.println(dependency.getGroupId() + " / " + dependency.getArtifactId() + " / " + dependency.getVersion() + " / " + dependency.getScope());
    	     System.out.println(dependency.getGroupId()+ ":" + dependency.getArtifactId());
    	 }*/
    	    
    	} finally {
    	    reader.close();
    	}
    }
}
