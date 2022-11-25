package com.shashi.threading.resourcesharingcriticalsection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ResourceSharingCriticalSectionApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ResourceSharingCriticalSectionApplication.class, args);

		InventoryCounter iC = new InventoryCounter();
		IncrementingThread iT = new IncrementingThread(iC);
		DecrementingThread dT = new DecrementingThread(iC);

		iT.start();
		dT.start();
		iT.join();
		dT.join();
		System.out.println(iC.getItem());
	}

	public static class IncrementingThread extends Thread {
		private InventoryCounter invC;

		public IncrementingThread(InventoryCounter invC) {
			this.invC = invC;
		}

		@Override
		public void run() {
			for (int i = 0; i < 1000; i++) {
				invC.increment();
			}
		}
	}

	public static class DecrementingThread extends Thread {
		private InventoryCounter invC;

		public DecrementingThread(InventoryCounter invC) {
			this.invC = invC;
		}

		@Override
		public void run() {
			for (int i = 0; i < 1000; i++) {
				invC.decrement();
			}
		}
	}

	public static class InventoryCounter {
		private int item;

		public int getItem() {
			return item;
		}

		public void increment() {
			this.item++;
		}

		public void decrement() {
			this.item--;
		}
	}

}
